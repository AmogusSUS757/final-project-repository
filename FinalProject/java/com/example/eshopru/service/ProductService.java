package com.example.eshopru.service;


import java.io.IOException;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import com.example.eshopru.config.AppConfig;
import com.example.eshopru.jwt.SecurityUtil;
import com.example.eshopru.model.ProductEntity;
import com.example.eshopru.model.UserEntity;
import com.example.eshopru.repository.ProductRepository;
import com.example.eshopru.repository.UserRepository;
import com.example.eshopru.request.ProductRequest;
import com.example.eshopru.response.ProductResponse;

import jakarta.persistence.Cacheable;

@Service
public class ProductService {
@Autowired
private ProductRepository product_rep;
@Autowired
private UserRepository user_rep;

	public List<ProductEntity> GetAllProducts(){
		return product_rep.findAll();
	}
	
	public List<ProductEntity> GetMyProducts(){
		String username = SecurityUtil.GetCurrentuser();
		UserEntity user_ent = user_rep.findByUsername(username).orElseThrow(()-> new RuntimeException("i think ur logged off bro"));
		return product_rep.findByOwnerId(user_ent.getId());
	}
	
	public ProductEntity GetMyProductById(Long id) {
	    ProductEntity product = product_rep.findById(id).orElseThrow(() -> new RuntimeException("ts doesnt exist"));
	    
	    String username = SecurityUtil.GetCurrentuser();
	    Long current_user_id = user_rep.findByUsername(username).orElseThrow(() -> new RuntimeException("i think ur logged off bro")).getId();

	    if (!product.getOwnerId().equals(current_user_id)) {
	        throw new RuntimeException("lil bro that aint ur product");
	    }

	    return product;
	}

	public Optional<ProductEntity> GetProductById(Long id) {
		return product_rep.findById(id);
	}
	
	public List<ProductEntity> SortByMinRating(int min_rating) {
		return product_rep.findAllByRatingGreaterThanEqual(min_rating);
	}
	
	public List<ProductEntity> SortByPriceAsc() {
		return product_rep.findByOrderByPriceAsc();
	}
	
	public List<ProductEntity> SortByPriceDesc() {
		return product_rep.findByOrderByPriceDesc();
	}
	
	public void SaveProduct(ProductRequest product) {
	    String username = SecurityUtil.GetCurrentuser();
	    UserEntity user_ent = user_rep.findByUsername(username).orElseThrow(() -> new RuntimeException("i think ur logged off bro"));
	    ProductEntity product_ent = new ProductEntity();

	    product_ent.setBrand(product.getBrand());
	    product_ent.setType(product.getType());
	    product_ent.setCategory(product.getCategory());
	    product_ent.setPrice(product.getPrice());
	    product_ent.setRating(product.getRating());
	    product_ent.setImage_url(product.getImage_url());
	    product_ent.setOwnerId(user_ent.getId());
	    product_ent.setDescription(product.getDescription());

	    product_rep.save(product_ent);
	}

	public void DeleteProductById(Long id) {
		ProductEntity product = product_rep.findById(id).orElseThrow(() -> new RuntimeException("ts doesnt exist"));
		product_rep.deleteById(id);
	}
	
	public void UpdateProduct(Long id, ProductRequest updatedProduct) {
	    ProductEntity existingProduct = GetMyProductById(id);
		ProductEntity product_ent = new ProductEntity();

	    existingProduct.setBrand(updatedProduct.getBrand());
	    existingProduct.setType(updatedProduct.getType());
	    existingProduct.setPrice(updatedProduct.getPrice());
	    existingProduct.setRating(updatedProduct.getRating());
	    existingProduct.setImage_url(updatedProduct.getImage_url());
	    existingProduct.setDescription(updatedProduct.getDescription());
	    product_rep.save(existingProduct);
	}

	public List<ProductEntity> SearchProduct(String keyword){
		return product_rep.findByBrandContainingIgnoreCase(keyword);
	}

	
}
