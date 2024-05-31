package com.ispan.recordshop.cochordnuts.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.dto.ProductDTO;
import com.ispan.recordshop.cochordnuts.model.Artist;
import com.ispan.recordshop.cochordnuts.model.Language;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.model.ProductStyle;
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
	@Autowired
	private ProductStyleService prodStyleService;
	@Autowired
	private LanguageService languageService;
//	@Autowired
//	private MusicYearService musicYearService;
	
	// 單一查詢回傳Product
	public Product findByIdReturnProduct(Integer id) {
		if(id == null) {
			return null;
		}
		Optional<Product> optional = productRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	
	
	// 單一查詢回傳ProductDTO
	public ProductDTO findById(Integer id) {
		if(id == null) {
			return null;
		}
		Optional<Product> optional = productRepo.findById(id);
		if (optional.isPresent()) {
			Product pro = optional.get();
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductNo(pro.getProductNo());
			productDTO.setProductName(pro.getProductName());
			productDTO.setUnitPrice(pro.getUnitPrice());
			productDTO.setStock(pro.getStock());
			productDTO.setProductStatus(pro.getProductStatus());
			productDTO.setDescribe(pro.getDescribe());
			productDTO.setPublishedDate(DatetimeConverter.toString(pro.getPublishedDate(), "yyyy-MM-dd"));
			productDTO.setDiscount(pro.getDiscount());
			productDTO.setIsBest(pro.getIsBest());
			productDTO.setIsDiscount(pro.getIsDiscount());
			productDTO.setIsPreorder(pro.getIsPreorder());
			productDTO.setPhoto(pro.getPhoto());
			productDTO.setStyleType(pro.getProductStyle().getStyleType());
			productDTO.setArtistType(pro.getArtist().getArtistName());
			productDTO.setLanguageType(pro.getLanguage().getLanguageType());
			productDTO.setMusicYear(pro.getMusicYear().getGeneration());
			return productDTO;
		}
		return null;
	}
	
	// 刪除商品
	public boolean delete(Integer id) {
		if(productRepo.existsById(id)) {
			productRepo.deleteById(id);
			return true;
		}
		return false;
	}
	
	// 查詢商品是否存在
	public boolean existById(Integer id) {
		if(id!=null) {
			return productRepo.existsById(id);
		}
		return false;
	}

	// 新增產品
	public Product insert(Product product) throws IOException {
		if(product != null ) {
			if(product.getPhoto() == null) {
				product.setPhoto(initializePhoto());
			}
			product.setLastModifiedDate(new Date());
			return productRepo.save(product);
		}
		return null;
	}
	
	// 沒圖片放入no-image
	public byte[] initializePhoto() throws IOException {
		byte[] buffer = new byte[8192];

		ClassLoader classLoader = getClass().getClassLoader();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedInputStream is = new BufferedInputStream(
				classLoader.getResourceAsStream("static/images/no-image.jpg"));
		int len = is.read(buffer);
		while(len!=-1) {
			os.write(buffer, 0, len);
			len = is.read(buffer);
		}
		is.close();
		return os.toByteArray();
    }


	// 多筆查詢分頁
	public Page<Product> findAllByPages(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 15, Sort.Direction.DESC, "productNo");
		return productRepo.findAllByPages(pageable);
		
	}

	// 查詢全部產品
	public List<ProductDTO> findAll(){
		List<Product> products = productRepo.findAll();
		List<ProductDTO> productsDTO = new ArrayList<>();
		ProductDTO productDTO = null;
		for(Product pro : products) {
			productDTO = new ProductDTO();
			productDTO.setProductNo(pro.getProductNo());
			productDTO.setProductName(pro.getProductName());
			productDTO.setUnitPrice(pro.getUnitPrice());
			productDTO.setStock(pro.getStock());
			productDTO.setProductStatus(pro.getProductStatus());
			productDTO.setDescribe(pro.getDescribe());
			productDTO.setPublishedDate(DatetimeConverter.toString(pro.getPublishedDate(), "yyyy-MM-dd"));
			productDTO.setDiscount(pro.getDiscount());
			productDTO.setPhoto(pro.getPhoto());
			productDTO.setStyleType(pro.getProductStyle().getStyleType());
			productDTO.setArtistType(pro.getArtist().getArtistName());
			productDTO.setLanguageType(pro.getLanguage().getLanguageType());
			productDTO.setMusicYear(pro.getMusicYear().getGeneration());
			productsDTO.add(productDTO);
		}
		return productsDTO;
	}
	
	// 修改產品
	public Product modify(Product newProduct) {
		if (newProduct != null && newProduct.getProductNo() != null) {
			Optional<Product> optional = productRepo.findById(newProduct.getProductNo());
			if (optional.isPresent()) {
				newProduct.setLastModifiedDate(new Date());
				return productRepo.save(newProduct);
			}
		}
		return null;
	}
	
	//多條件查詢 回傳ProductDTO
	public List<ProductDTO> search(JSONObject obj) {
		//判斷欄位是否空白
		Integer productNo = obj.isNull("productNo")? null : obj.getInt("productNo");
		String productName = obj.isNull("productName")? null : obj.getString("productName");
		Double startPrice = obj.isNull("startPrice") ? null : obj.getDouble("startPrice") ;
		Double endPrice = obj.isNull("endPrice") ? null : obj.getDouble("endPrice") ;
		String startDate = obj.isNull("startDate")? null : obj.getString("startDate");
		String endDate = obj.isNull("endDate")? null : obj.getString("endDate");
		String artistName = obj.isNull("artistName")? null : obj.getString("artistName");
		Integer productStatus = obj.isNull("productStatus")? null : obj.getInt("productStatus");
		Integer isBest = obj.isNull("isBest")? null : obj.getInt("isBest");
		Integer isDiscount = obj.isNull("isDiscount")? null : obj.getInt("isDiscount");
		Integer isPreorder = obj.isNull("isPreorder")? null : obj.getInt("isPreorder");
		JSONArray style = obj.isNull("style")? null : obj.getJSONArray("style");
		Integer language = obj.isNull("language")? null : obj.getInt("language");
		
		//設定排列預設值
		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int rows = obj.isNull("rows") ? 4 : obj.getInt("rows");
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
		if(productStatus != null) {
			predicates.add(criteriaBuilder.equal(table.get("productStatus"), productStatus));
		}
		if(isBest != null) {
			predicates.add(criteriaBuilder.equal(table.get("isBest"), isBest));
		}
		if(isDiscount != null) {
//			System.out.println(isDiscount);
			predicates.add(criteriaBuilder.equal(table.get("isDiscount"), isDiscount));
		}
		if(isPreorder != null) {
			predicates.add(criteriaBuilder.equal(table.get("isPreorder"), isPreorder));
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
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(table.get("publishedDate"), temp));
		}
		if(endDate != null) {
			java.util.Date temp = DatetimeConverter.parse(endDate, "yyyy-MM-dd");
			predicates.add(criteriaBuilder.lessThanOrEqualTo(table.get("publishedDate"), temp));
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
		if(style != null) {
			System.out.println(style);
			List<Integer> styleNos = new ArrayList<>();
			for(int i = 0; i < style.length(); i++) {
				styleNos.add(style.getInt(i));
			}
			predicates.add(table.get("productStyle").get("styleNo").in(styleNos));
//			predicates.add(criteriaBuilder.equal(table.get("productStyle"), style));
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
				.setFirstResult(start)
				.setMaxResults(rows);
		
		List<Product> products = typedQuery.getResultList();
		if(products != null && !products.isEmpty()) {
			List<ProductDTO> productsDTO = new ArrayList<>();
			ProductDTO productDTO = null;
			for(Product pro : products) {
				productDTO = new ProductDTO();
				productDTO.setProductNo(pro.getProductNo());
				productDTO.setProductName(pro.getProductName());
				productDTO.setUnitPrice(pro.getUnitPrice());
				productDTO.setDescribe(pro.getDescribe());
				productDTO.setPublishedDate(DatetimeConverter.toString(pro.getPublishedDate(), "yyyy-MM-dd"));
				productDTO.setDiscount(pro.getDiscount());
				productDTO.setPhoto(pro.getPhoto());
				productDTO.setStyleType(pro.getProductStyle().getStyleType());
				productDTO.setArtistType(pro.getArtist().getArtistName());
				productDTO.setLanguageType(pro.getLanguage().getLanguageType());
				productDTO.setMusicYear(pro.getMusicYear().getGeneration());
				productsDTO.add(productDTO);
			}
			return productsDTO;
			
		} else {
			return null;
		}
	}
	
	
	//多條件查詢 回傳結果數量 long
		public long searchCount(JSONObject obj) {
			//判斷欄位是否空白
			Integer productNo = obj.isNull("productNo")? null : obj.getInt("productNo");
			String productName = obj.isNull("productName")? null : obj.getString("productName");
			Double startPrice = obj.isNull("startPrice") ? null : obj.getDouble("startPrice") ;
			Double endPrice = obj.isNull("endPrice") ? null : obj.getDouble("endPrice") ;
			String startDate = obj.isNull("startDate")? null : obj.getString("startDate");
			String endDate = obj.isNull("endDate")? null : obj.getString("endDate");
			String artistName = obj.isNull("artistName")? null : obj.getString("artistName");
			Integer productStatus = obj.isNull("productStatus")? null : obj.getInt("productStatus");
			Integer isBest = obj.isNull("isBest")? null : obj.getInt("isBest");
			Integer isDiscount = obj.isNull("isDiscount")? null : obj.getInt("isDiscount");
			Integer isPreorder = obj.isNull("isPreorder")? null : obj.getInt("isPreorder");
			JSONArray style = obj.isNull("style")? null : obj.getJSONArray("style");
			Integer language = obj.isNull("language")? null : obj.getInt("language");
			
			//Criteria Connect
			CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			
			//from product table
			Root<Product> table = criteriaQuery.from(Product.class);
			
			//select count(*)
			criteriaQuery = criteriaQuery.select(criteriaBuilder.count(table));
			
			//where start
			List<Predicate> predicates = new ArrayList<>();
			
			if(productNo != null) {
				Predicate p1 = criteriaBuilder.equal(table.get("productNo"), productNo);
				predicates.add(p1);
			}
			if(productStatus != null) {
				predicates.add(criteriaBuilder.equal(table.get("productStatus"), productStatus));
			}
			if(isBest != null) {
				predicates.add(criteriaBuilder.equal(table.get("isBest"), isBest));
			}
			if(isDiscount != null) {
				System.out.println(isDiscount);
				predicates.add(criteriaBuilder.equal(table.get("isDiscount"), isDiscount));
			}
			if(isPreorder != null) {
				predicates.add(criteriaBuilder.equal(table.get("isPreorder"), isPreorder));
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
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(table.get("publishedDate"), temp));
			}
			if(endDate != null) {
				java.util.Date temp = DatetimeConverter.parse(endDate, "yyyy-MM-dd");
				predicates.add(criteriaBuilder.lessThanOrEqualTo(table.get("publishedDate"), temp));
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
			if(style != null) {
				System.out.println(style);
				List<Integer> styleNos = new ArrayList<>();
				for(int i = 0; i < style.length(); i++) {
					styleNos.add(style.getInt(i));
				}
				predicates.add(table.get("productStyle").get("styleNo").in(styleNos));
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
			
			TypedQuery<Long> typedQuery = this.getSession().createQuery(criteriaQuery);
					
			Long result = typedQuery.getSingleResult();
			
			if(result != null) {
				return result.longValue();
			} else {
				return 0;
			}
		}
	
	
	
	
	
	
	
	// 熱銷商品
	public List<ProductDTO> findIsBest(Integer isBest){
		List<Product> products = productRepo.findIsBest(isBest, 1);
		List<ProductDTO> productsDTO = new ArrayList<>();
		ProductDTO productDTO = null;
		for(Product pro : products) {
			productDTO = new ProductDTO();
			productDTO.setProductNo(pro.getProductNo());
			productDTO.setProductName(pro.getProductName());
			productDTO.setUnitPrice(pro.getUnitPrice());
			productDTO.setDescribe(pro.getDescribe());
			productDTO.setPublishedDate(DatetimeConverter.toString(pro.getPublishedDate(), "yyyy-MM-dd"));
			productDTO.setDiscount(pro.getDiscount());
			productDTO.setPhoto(pro.getPhoto());
			productDTO.setStyleType(pro.getProductStyle().getStyleType());
			productDTO.setArtistType(pro.getArtist().getArtistName());
			productDTO.setLanguageType(pro.getLanguage().getLanguageType());
			productDTO.setMusicYear(pro.getMusicYear().getGeneration());
			productsDTO.add(productDTO);
		}
		return productsDTO;
		
	}
	
	
	// 折扣商品
	public List<ProductDTO> findIsDiscount(Integer isDiscount){
		List<Product> products = productRepo.findIsDiscount(isDiscount, 1);
		List<ProductDTO> productsDTO = new ArrayList<>();
		ProductDTO productDTO = null;
		for(Product pro : products) {
			productDTO = new ProductDTO();
			productDTO.setProductNo(pro.getProductNo());
			productDTO.setProductName(pro.getProductName());
			productDTO.setUnitPrice(pro.getUnitPrice());
			productDTO.setDescribe(pro.getDescribe());
			productDTO.setPublishedDate(DatetimeConverter.toString(pro.getPublishedDate(), "yyyy-MM-dd"));
			productDTO.setDiscount(pro.getDiscount());
			productDTO.setPhoto(pro.getPhoto());
			productDTO.setStyleType(pro.getProductStyle().getStyleType());
			productDTO.setArtistType(pro.getArtist().getArtistName());
			productDTO.setLanguageType(pro.getLanguage().getLanguageType());
			productDTO.setMusicYear(pro.getMusicYear().getGeneration());
			productsDTO.add(productDTO);
		}
		return productsDTO;
	}
	
	// 預購商品
	public List<ProductDTO> findIsPreorder(Integer isPreorder){
		List<Product> products = productRepo.findIsPreorder(isPreorder, 1);
		List<ProductDTO> productsDTO = new ArrayList<>();
		ProductDTO productDTO = null;
		for(Product pro : products) {
			productDTO = new ProductDTO();
			productDTO.setProductNo(pro.getProductNo());
			productDTO.setProductName(pro.getProductName());
			productDTO.setUnitPrice(pro.getUnitPrice());
			productDTO.setDescribe(pro.getDescribe());
			productDTO.setPublishedDate(DatetimeConverter.toString(pro.getPublishedDate(), "yyyy-MM-dd"));
			productDTO.setDiscount(pro.getDiscount());
			productDTO.setPhoto(pro.getPhoto());
			productDTO.setStyleType(pro.getProductStyle().getStyleType());
			productDTO.setArtistType(pro.getArtist().getArtistName());
			productDTO.setLanguageType(pro.getLanguage().getLanguageType());
			productDTO.setMusicYear(pro.getMusicYear().getGeneration());
			productsDTO.add(productDTO);
		}
		return productsDTO;
	}
	
	// 依語言查詢商品 華語/日韓/西洋
		public List<ProductDTO> findByLanguage(Integer languageNo){
			Language language = languageService.findById(languageNo);
			List<Product> products = productRepo.findByLanguage(language, 1);
			List<ProductDTO> productsDTO = new ArrayList<>();
			ProductDTO productDTO = null;
			for(Product pro : products) {
				productDTO = new ProductDTO();
				productDTO.setProductNo(pro.getProductNo());
				productDTO.setProductName(pro.getProductName());
				productDTO.setUnitPrice(pro.getUnitPrice());
				productDTO.setDescribe(pro.getDescribe());
				productDTO.setPublishedDate(DatetimeConverter.toString(pro.getPublishedDate(), "yyyy-MM-dd"));
				productDTO.setDiscount(pro.getDiscount());
				productDTO.setPhoto(pro.getPhoto());
				productDTO.setStyleType(pro.getProductStyle().getStyleType());
				productDTO.setArtistType(pro.getArtist().getArtistName());
				productDTO.setLanguageType(pro.getLanguage().getLanguageType());
				productDTO.setMusicYear(pro.getMusicYear().getGeneration());
				productsDTO.add(productDTO);
			}
			return productsDTO;
		}
	
		// 依音樂類型查詢商品 流行/搖滾
		public List<ProductDTO> findByStyle(Integer styleNo){
			ProductStyle style = prodStyleService.findById(styleNo);
			List<Product> products = productRepo.findByStyle(style, 1);
			List<ProductDTO> productsDTO = new ArrayList<>();
			ProductDTO productDTO = null;
			for(Product pro : products) {
				productDTO = new ProductDTO();
				productDTO.setProductNo(pro.getProductNo());
				productDTO.setProductName(pro.getProductName());
				productDTO.setUnitPrice(pro.getUnitPrice());
				productDTO.setDescribe(pro.getDescribe());
				productDTO.setPublishedDate(DatetimeConverter.toString(pro.getPublishedDate(), "yyyy-MM-dd"));
				productDTO.setDiscount(pro.getDiscount());
				productDTO.setPhoto(pro.getPhoto());
				productDTO.setStyleType(pro.getProductStyle().getStyleType());
				productDTO.setArtistType(pro.getArtist().getArtistName());
				productDTO.setLanguageType(pro.getLanguage().getLanguageType());
				productDTO.setMusicYear(pro.getMusicYear().getGeneration());
				productsDTO.add(productDTO);
			}
			return productsDTO;
		}
		
		
		
		
	

}
