package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository<EntityName, PrimaryKeyType> Interface provide us  with readyMade curd Operation for any Entity..
// We don't have to Write the implementtation Code for this Interface Jpa will generate implementation automatically
// at runtime
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByCategoryName(@NotBlank String categoryName);
}
