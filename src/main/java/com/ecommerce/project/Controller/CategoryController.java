package com.ecommerce.project.Controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.serviice.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/public/categories", "/api/public/Categories"})
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();

    }
    @PostMapping({"/public/categories", "/api/public/Categories"})
    public String createCategory( @Valid  @RequestBody Category category){
       categoryService.createCategory(category);
       return "Category Created Successfully";

    }

    @DeleteMapping({"/admin/categories/{categoryId}", "/api/admin/Categories/{categoryId}"})
    public String deleteCategory(@PathVariable long categoryId){

        String status = categoryService.deleteCategory(categoryId);
        return status;
    }
    @PutMapping({"/public/categories/{categoryId}", "/api/public/Categories/{categoryId}"})
    public ResponseEntity<String> updateCategory( @Valid @RequestBody Category category, @PathVariable Long categoryId){


           Category savedCategory = categoryService.updateCategory(category, categoryId);
           return new ResponseEntity<>("Category with CategoryId : " + savedCategory.getCategoryId() + " Updated Successfully", HttpStatus.OK);



    }




}
