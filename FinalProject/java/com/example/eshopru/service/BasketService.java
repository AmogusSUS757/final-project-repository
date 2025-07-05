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
import com.example.eshopru.model.BasketEntity;
import com.example.eshopru.model.ProductEntity;
import com.example.eshopru.model.UserEntity;
import com.example.eshopru.repository.BasketRepository;
import com.example.eshopru.repository.ProductRepository;
import com.example.eshopru.repository.UserRepository;
import com.example.eshopru.request.ProductRequest;
import com.example.eshopru.response.ProductResponse;

import jakarta.persistence.Cacheable;
import jakarta.transaction.Transactional;

@Service
public class BasketService {
	@Autowired
	BasketRepository basket_rep;
	@Autowired
	UserRepository user_rep;
	@Autowired
	ProductRepository product_rep;
	
	public void addToBasket(Long product_id) {
		String username = SecurityUtil.GetCurrentuser();
		UserEntity user = user_rep.findByUsername(username).orElseThrow(() -> new RuntimeException("I think ur username is wrong bro"));
		BasketEntity basket = basket_rep.findByUserIdAndProductId(user.getId(), product_id).orElse(new BasketEntity());
		basket.setUserId(user.getId());
		basket.setProductId(product_id);
		
		if(basket.getQuantity()== null) {
			basket.setQuantity((long) 1);
		} else {
			basket.setQuantity(basket.getQuantity() + 1);
		}
		basket_rep.save(basket);
	}
	
	@Transactional
	public void removeFrombasket(Long product_id) {
		String username = SecurityUtil.GetCurrentuser();
		UserEntity user = user_rep.findByUsername(username).orElseThrow(() -> new RuntimeException("I think ur username is wrong bro"));
		basket_rep.deleteByUserIdAndProductId(user.getId(), product_id);
	}
	
	public List<BasketEntity> getMyBasket() {
		String username = SecurityUtil.GetCurrentuser();
		UserEntity user = user_rep.findByUsername(username).orElseThrow(() -> new RuntimeException("I think ur username is wrong bro"));
		List<BasketEntity> baskets = basket_rep.findByUserId(user.getId());
		return baskets;
	}
}
