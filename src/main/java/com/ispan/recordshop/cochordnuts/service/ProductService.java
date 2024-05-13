package com.ispan.recordshop.cochordnuts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;
import com.ispan.recordshop.cochordnuts.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class ProductService {
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ArtistService artistService;

	// 單一查詢
	public Product findById(Integer id) {
		if(id == null) {
			return null;
		}
		Optional<Product> optional = productRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	// 新增產品
	public Product insert(Product product) {
		if(product != null ) {
			return productRepo.save(product);
		}
		return null;
	}

	// 多筆查詢分頁
	public Page<Product> findAllByPages(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 15, Sort.Direction.DESC, "productNo");
		return productRepo.findAllByPages(pageable);

	}

	// 查詢全部產品
	public List<Product> findAll(){
		return productRepo.findAll();
	}
	
	// 修改產品
	public Product modify(Product newProduct) {
		if (newProduct != null && newProduct.getProductNo() != null) {
			Optional<Product> optional = productRepo.findById(newProduct.getProductNo());
			if (optional.isPresent()) {
				return productRepo.save(newProduct);
			}
		}
		return null;
	}
	
	//多條件查詢
	public List<Product> search(JSONObject obj) {
		//判斷欄位是否空白
		Integer productNo = obj.isNull("productNo")? null : obj.getInt("productNo");
		String productName = obj.isNull("productName")? null : obj.getString("productName");
		Double startPrice = obj.isNull("startPrice") ? null : obj.getDouble("startPrice") ;
		Double endPrice = obj.isNull("endPrice") ? null : obj.getDouble("endPrice") ;
		String startDate = obj.isNull("startDate")? null : obj.getString("startDate");
		String endDate = obj.isNull("endDate")? null : obj.getString("endDate");
		String artistName = obj.isNull("artistName")? null : obj.getString("artistName");
		Integer style = obj.isNull("style")? null : obj.getInt("style");
		Integer language = obj.isNull("language")? null : obj.getInt("language");
		if(obj.isEmpty()) {
			System.out.println("空的");
		}
		//設定排列預設值
		int rows = obj.isNull("rows") ? 15 : obj.getInt("rows");
		String order = obj.isNull("order") ? "productNo" : obj.getString("order");
		boolean direction = obj.isNull("direction") ? false : obj.getBoolean("direction");
		
		//Criteria Connect
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		
		//from product table
		Root<Product> table = criteriaQuery.from(Product.class);
		
		//where start
		List<Predicate> predicates = new ArrayList<>();
		
		if(productNo != null) {
			Predicate p1 = criteriaBuilder.equal(table.get("productNo"), productNo);
			predicates.add(p1);
		}
		if(productName != null && productName.length() != 0) {
			System.out.println(productName);
			Predicate p2 = criteriaBuilder.like(table.get("productName"), "%"+productName+"%");
			predicates.add(p2);
		}
		if(startPrice != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(table.get("unitPrice"), startPrice));
		}
		if(endPrice != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(table.get("unitPrice"), endPrice));
		}
		//日期比較 處理~~~~
		if(startDate != null) {
			java.util.Date temp = DatetimeConverter.parse(startDate, "yyyy-MM-dd");
//			java.sql.Date temp1 = new java.sql.Date(temp.getTime());
//			System.err.println(startDate);
//			System.out.println(temp);
			predicates.add(criteriaBuilder.greaterThan(table.get("publishedDate"), temp));
		}
		if(endDate != null) {
			java.util.Date temp = DatetimeConverter.parse(endDate, "yyyy-MM-dd");
			predicates.add(criteriaBuilder.lessThan(table.get("publishedDate"), temp));
		}
		if(artistName != null && artistName.length() != 0) {
			System.out.println(artistName);
			List<Artist> artists = artistService.findByName(artistName);
			if(!artists.isEmpty()) {
				List<Integer> artistNos = new ArrayList<>();
				for(Artist artist : artists) {
					artistNos.add(artist.getArtistNo());
				}
				predicates.add(table.get("artist").get("artistNo").in(artistNos));
			} else {
				predicates.add(criteriaBuilder.equal(table.get("artist").get("artistNo"), 0));
			}
		}
		//下拉選項用數字對照
		if(style != null && style != 0) {
			predicates.add(criteriaBuilder.equal(table.get("productStyle"), style));
		}
		//下拉選項用數字對照
		if(language != null && language != 0) {
			predicates.add(criteriaBuilder.equal(table.get("language"), language));
		}
		
		if(predicates != null && !predicates.isEmpty()) {
			Predicate[] array = predicates.toArray(new Predicate[0]);
			criteriaQuery = criteriaQuery.where(array);
		}
		//where end		
		
		//order by
		if(direction) {
			criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get(order)));
		} else {
			criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(table.get(order)));
		}
		
		TypedQuery<Product> typedQuery = this.getSession().createQuery(criteriaQuery)
				.setMaxResults(rows);
		
		List<Product> result = typedQuery.getResultList();
		if(result != null && !result.isEmpty()) {
//			System.out.println(result.toString());
//			System.out.println(result.size());
			return result;
		} else {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	

}
