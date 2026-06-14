package com.ecommerce.project.Controller;


import com.ecommerce.project.payLoad.ProductDTO;
import com.ecommerce.project.payLoad.ProductResponse;
import com.ecommerce.project.serviice.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @GetMapping("/public/categories/{categoryId}/Products")
public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){

      ProductResponse productResponse = productService.getProductsByCategory(categoryId);

      return new ResponseEntity<>(productResponse, HttpStatus.OK);

}
@GetMapping("/public/Products/keyword/{Keyword}")
public ResponseEntity<ProductResponse> getProductsByKeyword( @PathVariable String Keyword){
    ProductResponse productResponse = productService.getProductsByKeyword(Keyword);

    return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
}

@PutMapping("/admin/Products/{productId}")
public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                @PathVariable Long productId){

    ProductDTO savedProduct = productService.updateProduct(productDTO, productId);
    return new ResponseEntity<>(savedProduct, HttpStatus.OK);

}

@DeleteMapping("/admin/Products/{productId}")
public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){

        ProductDTO deleteProduct = productService.deleteProduct(productId);

        return new ResponseEntity<>(deleteProduct, HttpStatus.OK);

}
@PutMapping("/Products/{productId}/Image")
public ResponseEntity<ProductDTO> updateProductImage (@PathVariable Long productId,
                                                     @RequestParam("Image")MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

}

}
