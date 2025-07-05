package com.example.eshopru.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eshopru.model.AuthorityEntity;
import com.example.eshopru.model.UserEntity;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
	Optional<AuthorityEntity> findByUsername(String username);
}
