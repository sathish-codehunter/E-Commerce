package com.sathish.shop.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sathish.shop.exceptions.AlreadyExistException;
import com.sathish.shop.exceptions.CategoryNotFoundException;
import com.sathish.shop.model.Category;
import com.sathish.shop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(category.getName()))
                .map(categoryRepository::save).orElseThrow(() -> new AlreadyExistException(category.getName() + "Already exists"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, ()->{
            throw new CategoryNotFoundException("Category Not Found");
       });
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));
    }
    
}
