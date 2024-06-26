package com.ispan.recordshop.cochordnuts.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.recordshop.cochordnuts.dto.ProductDTO;
import com.ispan.recordshop.cochordnuts.model.Product;
import com.ispan.recordshop.cochordnuts.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	private byte[] photo = null;

	//新增產品
	@PostMapping("/products/create")
//	public void createProduct(@RequestBody Product product) {
	public ResponseEntity<?> createProduct(@RequestBody Product product) throws IOException {
		if(product != null) {
			ProductDTO result = productService.findById(product.getProductNo());
			if(result == null) {
				Product newProd = productService.insert(product);
				if(newProd != null) {
					String uri = "http://localhost:8080/products/create"+product.getProductNo();
					return ResponseEntity.created(URI.create(uri))
							.contentType(MediaType.APPLICATION_JSON)
							.body(newProd);
				}
			}
		}
		return ResponseEntity.noContent().build();
	}
	
	//商品修改
	@PutMapping("/products/modify/{id}")
	public ResponseEntity<?> modifyProduct(@PathVariable Integer id, @RequestBody Product product) {
		if(product != null && product.getProductNo() != null && product.getProductNo() != 0) {
			ProductDTO result = productService.findById(product.getProductNo());
			if(result != null) {
				Product newProd = productService.modify(product);
				if(newProd != null) {
					String uri = "http://localhost:8080/products/modify"+product.getProductNo();
					return ResponseEntity.created(URI.create(uri))
							.contentType(MediaType.APPLICATION_JSON)
							.body(newProd.toString());
				}
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	// 刪除商品
	@DeleteMapping("/products/remove/{id}")
	public ResponseEntity<Void> remove(@PathVariable Integer id){
		if(id != null && id != 0) {
			boolean result = productService.existById(id);
			if(result) {
				if(productService.delete(id))
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	// 拿圖片
	@GetMapping(path="/products/photo/{id}", produces= {MediaType.IMAGE_JPEG_VALUE})
	public @ResponseBody byte[] getPhoto(@PathVariable Integer id) {
		ProductDTO result = productService.findById(id);
		byte[] prodPhoto = this.photo;
		if(result != null) {
			prodPhoto = result.getPhoto();
		}
		return prodPhoto;
	}
	
	//多條件查詢 傳回List<ProductDTO>
	@PostMapping("/products/search")
	public ResponseEntity<?> searchByCondition(@RequestBody String obj) {
		JSONObject json = new JSONObject(obj);
		List<ProductDTO> result = productService.search(json);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	
	}
	
	//多條件查詢 傳回結果個數
	@PostMapping("/products/searchCount")
	public ResponseEntity<?> searchCountByCondition(@RequestBody String obj) {
		JSONObject json = new JSONObject(obj);
		long resultCount = productService.searchCount(json);
		if(resultCount != 0) {
			return ResponseEntity.ok(resultCount);
		} else {
			return ResponseEntity.notFound().build();
		}
	
	}
	
	// 查詢全部
	@GetMapping("/products/findAll")
	public ResponseEntity<?> findAll(){
		List<ProductDTO> result = productService.findAll();
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	// 查詢全部分頁
	@GetMapping("/products/findAllByPages/{page}")
	public ResponseEntity<?> findAllByPages(@PathVariable Integer page){
		Page<Product> result = productService.findAllByPages(page);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	// 單一查詢回傳ProductDTO
	@GetMapping("/products/detail/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ProductDTO result = productService.findById(id);
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	// 單一查詢回傳Product
	@GetMapping("/products/detailProduct/{id}")
	public ResponseEntity<?> findByIdReturnProduct(@PathVariable Integer id) {
		Product result = productService.findByIdReturnProduct(id);
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	// 是否熱銷
	@GetMapping("/products/isBest")
	public ResponseEntity<?> isBest(){
		List<ProductDTO> result = productService.findIsBest(1);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	// 是否折扣
	@GetMapping("/products/isDiscount")
	public ResponseEntity<?> isDiscount(){
		List<ProductDTO> result = productService.findIsDiscount(1);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	// 是否預購
	@GetMapping("/products/isPreorder")
	public ResponseEntity<?> isPreorder(){
		List<ProductDTO> result = productService.findIsPreorder(1);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	// 依語言查詢商品 華語/日韓/西洋
	@GetMapping("/products/languageFind/{languageNo}")
	public ResponseEntity<?> languageFind(@PathVariable Integer languageNo){
		System.out.println(languageNo);
		List<ProductDTO> result = productService.findByLanguage(languageNo);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	} 
	
	// 依語言查詢商品 華語/日韓/西洋 無限滾動分頁
	@GetMapping("/products/languageFindInf/{languageNo}")
	public ResponseEntity<?> languageFindTest(
			@PathVariable Integer languageNo,
			@RequestParam int page,
	        @RequestParam int size){
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<ProductDTO> result = productService.findByLanguageInf(languageNo, pageable);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	} 
	
	
	// 依音樂類型查詢商品 流行/搖滾 
	@GetMapping("/products/styleFind/{styleNo}")
	public ResponseEntity<?> styleFind(@PathVariable Integer styleNo){
		List<ProductDTO> result = productService.findByStyle(styleNo);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	} 
	
	// 依音樂類型查詢商品 流行/搖滾 無限滾動分頁
	@GetMapping("/products/styleFindInf/{styleNo}")
	public ResponseEntity<?> styleFindInf(
			@PathVariable Integer styleNo,
			@RequestParam int page,
	        @RequestParam int size){
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<ProductDTO> result = productService.findByStyleInf(styleNo, pageable);
		if(result != null && !result.isEmpty()) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	} 
	
	


}
