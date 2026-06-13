package com.ecommerce.project.Controller;


import com.ecommerce.project.payLoad.ProductDTO;
import com.ecommerce.project.payLoad.ProductResponse;
import com.ecommerce.project.serviice.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

@Autowired
   ProductService productService;

    @PostMapping({
            "/admin/categories/{categoryId}/products",
            "/admin/categories/{categoryId}/product",
            "/admin/categories/{categoryId}/Product"
    })
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO,
                                                    @PathVariable Long categoryId){

ProductDTO savedprOductDTO = productService.createProduct(productDTO, categoryId);

     return new ResponseEntity<>(savedprOductDTO, HttpStatus.CREATED);

    }

    @GetMapping("/public/Products")
    public ResponseEntity<ProductResponse> getAllProducts(){
   ProductResponse productResponse = productService.getAllProducts();

   return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }



}
