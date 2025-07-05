package com.example.eshopru.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eshopru.model.BasketEntity;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
	List<BasketEntity> findByUserId(Long userId);
	Optional<BasketEntity> findByUserIdAndProductId(Long userId, Long productId);
	void deleteByUserIdAndProductId(Long userId, Long productId);
}
