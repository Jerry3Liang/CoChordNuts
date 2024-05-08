package com.ispan.recordshop.cochordnuts.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	// 單一查詢
	public Product findById(Integer id) {
		Optional<Product> optional = productRepo.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	// 新增產品
	public Product insert(Product product) {
		if (product != null && product.getProductNo() != null) {
			return productRepo.save(product);
		}
		return null;
	}

	// 多筆查詢分頁
	public Page<Product> findAllByPages(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 15, Sort.Direction.DESC, "productNo");
		return productRepo.findAllByPages(pageable);

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
	
	
	
	

}
