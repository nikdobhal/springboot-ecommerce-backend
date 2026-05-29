package com.ecommerce.project.Controller;


import com.ecommerce.project.payLoad.CategoryDTO;
import com.ecommerce.project.payLoad.CategoryResponse;
import com.ecommerce.project.serviice.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;


    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/public/categories", "/public/Categories"})
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse categoryResponse =categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);

    }
    @PostMapping({"/public/categories", "/public/Categories"})
    public ResponseEntity<CategoryDTO> createCategory( @Valid  @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
       return new ResponseEntity<>( savedCategoryDTO, HttpStatus.CREATED);

    }

    @DeleteMapping({"/admin/categories/{categoryId}", "/admin/Categories/{categoryId}"})
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable long categoryId){

      CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }
    @PutMapping({"/public/categories/{categoryId}", "/public/Categories/{categoryId}"})
    public ResponseEntity<CategoryDTO> updateCategory( @Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId){


           CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
           return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);



    }




}
