package com.ecommerce.project.serviice;

import com.ecommerce.project.payLoad.ProductDTO;
import com.ecommerce.project.payLoad.ProductResponse;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);
}
