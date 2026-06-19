package com.ecommerce.project.serviice;

import com.ecommerce.project.Exceptions.APIException;
import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payLoad.CategoryDTO;
import com.ecommerce.project.payLoad.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories(Integer pageNumber,  Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByandOrder = sortOrder.equalsIgnoreCase("asc")
                              ? Sort.by(sortBy).ascending()
                              : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByandOrder );

        Page<Category> pageCategory = categoryRepository.findAll(pageDetails);
       List<Category> categories = pageCategory.getContent();

       if(categories.isEmpty()){
           throw new APIException("Category is not Created yet");
       }

       List<CategoryDTO> categoryDtos = categories.stream()
               .map(category -> modelMapper.map(category, CategoryDTO.class))
               .toList();
       CategoryResponse Categoryresponse = new CategoryResponse();
        Categoryresponse.setContent(categoryDtos);
        Categoryresponse.setPageNumber(pageCategory.getNumber());
        Categoryresponse.setPageSize(pageCategory.getSize());
        Categoryresponse.setTotalElements(pageCategory.getTotalElements());
        Categoryresponse.setTotalPages(pageCategory.getTotalPages());
        Categoryresponse.setLastPage(pageCategory.isLast());

        return Categoryresponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb != null){
            throw new APIException("Category with the name " +category.getCategoryName() + " already exists!!");

        }
       Category savedCategory = categoryRepository.save(category);

        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
return savedCategoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Category  category = categoryRepository.findById(categoryId)
                              .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        categoryRepository.delete(category);
        return categoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                          .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        savedCategory.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory = categoryRepository.save(savedCategory);


        return modelMapper.map(updatedCategory, CategoryDTO.class);

    }



}
