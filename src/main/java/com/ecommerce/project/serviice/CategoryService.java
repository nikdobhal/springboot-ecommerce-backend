package com.ecommerce.project.serviice;

import com.ecommerce.project.payLoad.CategoryDTO;
import com.ecommerce.project.payLoad.CategoryResponse;


public interface CategoryService {
   CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
   CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
