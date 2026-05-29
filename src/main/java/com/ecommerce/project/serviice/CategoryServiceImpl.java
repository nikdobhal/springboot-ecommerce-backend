package com.ecommerce.project.serviice;

import com.ecommerce.project.Exceptions.APIException;
import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payLoad.CategoryDTO;
import com.ecommerce.project.payLoad.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories() {
       List<Category> categories = categoryRepository.findAll();

       if(categories.isEmpty()){
           throw new APIException("No Category Created till now.");

       }

       List<CategoryDTO> categoryDtos = categories.stream()
               .map(category -> modelMapper.map(category, CategoryDTO.class))
               .toList();
       CategoryResponse Categoryresponse = new CategoryResponse();
        Categoryresponse.setcontent(categoryDtos);
        return Categoryresponse;
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
