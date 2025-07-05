package com.example.eshopru.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshopru.exception.MyException;
import com.example.eshopru.jwt.JwtUtil;
import com.example.eshopru.model.ProductEntity;
import com.example.eshopru.request.ProductRequest;
import com.example.eshopru.response.ProductResponse;
import com.example.eshopru.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "*"})
public class ProductController {

	@Autowired
	ProductService service;
	
	@GetMapping(value = "/my-products")
	public List<ProductEntity> getmyProducts() {
		return service.GetMyProducts();
	}
	
	@GetMapping(value = "/all")
	public List<ProductEntity> getProducts() {
		return service.GetAllProducts();
	}
	
	@GetMapping("/{id}")
	public Optional<ProductEntity> FindMyProductById(@PathVariable Long id) {
	    return Optional.ofNullable(service.GetMyProductById(id));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/save")
	public void CreateProduct(@Valid @RequestBody ProductRequest product) {
		 service.SaveProduct(product);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/delete/{id}")
	public void DeleteProduct(@PathVariable Long id) {
		service.DeleteProductById(id);
	}	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/update/{id}")
	public void UpdateProduct(@PathVariable Long id, @RequestBody ProductRequest product) {
	    service.UpdateProduct(id, product);
	}

	@GetMapping("/search")
	public List<ProductEntity> SearchProduct(@RequestParam String q){
		return service.SearchProduct(q);
	}
	
	@GetMapping("/rating")
	public List<ProductEntity> SearchByMinRating(@RequestParam int min_rating) {
		return service.SortByMinRating(min_rating);
	}
	
	@GetMapping("/price/asc")
	public List<ProductEntity> SearchByPriceAsc() {
		return service.SortByPriceAsc();
	}
	
	@GetMapping("/price/desc")
	public List<ProductEntity> SearchByPriceDesc() {
		return service.SortByPriceDesc();
	}
}
