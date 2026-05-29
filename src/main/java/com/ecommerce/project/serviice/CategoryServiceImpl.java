package com.ecommerce.project.serviice;

import com.ecommerce.project.Exceptions.APIException;
import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
       List<Category> categories = categoryRepository.findAll();

       if(categories.isEmpty()){
           throw new APIException("No Category Created till now.");

       }

        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null){
            throw new APIException("Category with the name " +category.getCategoryName() + " already exists!!");

        }
       categoryRepository.save(category);

    }

    @Override
    public String deleteCategory(Long categoryId) {

        Category  category = categoryRepository.findById(categoryId)
                              .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        categoryRepository.delete(category);
        return "Category With CategoryId : " + categoryId + " Deleted Successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category  savedcategory = categoryRepository.findById(categoryId)
                          .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        category.setCategoryId(categoryId);
        savedcategory = categoryRepository.save(category);
        return savedcategory;

    }
}
