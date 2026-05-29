package com.ecommerce.project.serviice;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payLoad.CategoryResponse;

import java.util.List;

public interface CategoryService {
   CategoryResponse getAllCategories();
    void createCategory(Category category);
   String deleteCategory(Long categoryId);
    Category updateCategory(Category category, Long categoryId);
}
