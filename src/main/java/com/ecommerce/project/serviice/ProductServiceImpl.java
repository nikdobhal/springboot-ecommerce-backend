package com.ecommerce.project.serviice;

import com.ecommerce.project.Exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payLoad.ProductDTO;
import com.ecommerce.project.payLoad.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
   private  CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDTO createProduct(ProductDTO productDTO, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        //  Map DTO → Entity
        Product product = modelMapper.map(productDTO, Product.class);

        product.setImage("Default.png");
        product.setCategory(category);

        //  Calculate specialPrice server-side
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);

        //  Map saved Entity → DTO (includes calculated specialPrice)
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productsDTO = products.stream()
                .map(product ->modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productsDTO);

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productsDTO = products.stream()
                .map(product ->modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productsDTO);

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByproductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productsDTO = products.stream()
                .map(product ->modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productsDTO);

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
    Product savedProduct = productRepository.findById(productId)
          .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        savedProduct.setProductName(productDTO.getProductName());
        savedProduct.setDescription(productDTO.getDescription());
        savedProduct.setQuantity(productDTO.getQuantity());
        savedProduct.setPrice(productDTO.getPrice());
        savedProduct.setDiscount(productDTO.getDiscount());
        savedProduct.setSpecialPrice(productDTO.getSpecialPrice());
        Product updatedProduct = productRepository.save(savedProduct);

        return   modelMapper.map(updatedProduct, ProductDTO.class) ;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));


      productRepository.delete(product);


        return  modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //Get Product from database

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        //Upload Image to Server
        //Get the file name of uploaded image
        String Path = "images/";
        String filename = uploadImage(Path, image);

        //Updating new file name to the product
        product.setImage(filename);

        // save Product
        Product updatedProduct = productRepository.save(product);





        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
         // File name of current /original File
        String originalFileName = file.getOriginalFilename();
        // Generate  Unique names for file
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.pathSeparator + fileName;
        // Check if Path Exists OtherWise Create it
      File folder = new File(path);
      if(!folder.exists()){
          folder.mkdir();

      }

        // Upload file to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // Return the generated file name
        return fileName;

    }


}
