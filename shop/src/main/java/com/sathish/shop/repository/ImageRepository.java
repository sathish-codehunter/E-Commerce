package com.sathish.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sathish.shop.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    
}
