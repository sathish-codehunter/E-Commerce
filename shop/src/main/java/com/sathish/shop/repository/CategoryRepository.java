package com.sathish.shop.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.sathish.shop.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{

    Category findByName(Category category);
    Category findByName(String name);
    boolean existsByName(String name);
    
}
