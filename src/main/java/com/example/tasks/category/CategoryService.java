package com.example.tasks.category;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        if (categoryRepository.existsById(id)) {
            return categoryRepository.findById(id);
        } else {
            throw new RuntimeException("Category with ID" + id + "not found");
        }
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Optional<Category> optionalCategory = categoryRepository.findByName(updatedCategory.getName());
        if (optionalCategory.isPresent()) {
            throw new RuntimeException("Category with name " + updatedCategory.getName() + " already exists");
        }
        return categoryRepository.findById(id).map(
                category -> {
                    category.setName(updatedCategory.getName());
                    return categoryRepository.save(category);
                }
        ).orElseThrow(() -> new RuntimeException("Category with name " + updatedCategory.getName() + " not found"));
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Category with ID" + id + "not found");
        }
    }
}
