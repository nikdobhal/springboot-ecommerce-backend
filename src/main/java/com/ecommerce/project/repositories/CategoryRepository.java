package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<EntityName, PrimaryKeyType> Interface provide us  with readyMade curd Operation for any Entity..
// We don't have to Write the implementtation Code for this Interface Jpa will generate implementation automatically
// at runtime
public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByCategoryName(@NotBlank String categoryName);
}
