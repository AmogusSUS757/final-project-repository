package com.example.eshopru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eshopru.model.BasketEntity;
import com.example.eshopru.service.BasketService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/basket")
public class BasketController {
@Autowired
BasketService basket_serv;

@PostMapping("/add/{product_id}")
@PreAuthorize("hasRole('ROLE_USER')")
public void AddToBasket(@PathVariable Long product_id) {
	basket_serv.addToBasket(product_id);
}

@PreAuthorize("hasRole('ROLE_USER')")
@DeleteMapping("/delete/{product_id}")
public void RemoveFrombasket(@PathVariable Long product_id) {
	basket_serv.removeFrombasket(product_id);
}

@GetMapping("/my_basket")
@PreAuthorize("hasRole('ROLE_USER')")
public List<BasketEntity> MyBasket() {
	return basket_serv.getMyBasket();
}
}
