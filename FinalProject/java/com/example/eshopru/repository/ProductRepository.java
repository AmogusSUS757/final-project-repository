package com.example.eshopru.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eshopru.model.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	List<ProductEntity> findByOrderByPriceAsc();
	List<ProductEntity> findByOrderByPriceDesc();
    List<ProductEntity> findByBrandContainingIgnoreCase(String brand);
    List<ProductEntity> findAllByOrderByRatingDesc();
    List<ProductEntity> findAllByRatingGreaterThanEqual(int min_rating);
    
    Page<ProductEntity> findAll(Pageable pageable);
    
    List<ProductEntity> findByOwnerId(Long ownerId);
}

