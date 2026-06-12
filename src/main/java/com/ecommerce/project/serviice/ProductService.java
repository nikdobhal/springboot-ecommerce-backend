package com.ecommerce.project.serviice;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payLoad.ProductDTO;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO, Long categoryId);
}
