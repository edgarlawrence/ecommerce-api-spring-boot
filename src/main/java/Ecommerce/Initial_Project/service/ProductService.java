package Ecommerce.Initial_Project.service;

import Ecommerce.Initial_Project.dto.request.ProductCategoryRequestDTO;
import Ecommerce.Initial_Project.dto.request.ProductRequestDTO;
import Ecommerce.Initial_Project.dto.response.ProductCategoryResponseDTO;
import Ecommerce.Initial_Project.dto.response.ProductResponseDTO;
import Ecommerce.Initial_Project.model.Category;
import Ecommerce.Initial_Project.model.Product;
import Ecommerce.Initial_Project.model.ProductCategory;
import Ecommerce.Initial_Project.repository.CategoryRepository;
import Ecommerce.Initial_Project.repository.ProductCategoryRepository;
import Ecommerce.Initial_Project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private String saveImageFile(MultipartFile file) throws IOException {
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    public List<ProductResponseDTO> getAll() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> ProductResponseDTO.builder()
                        .id(product.getId())
                        .title(product.getTitle())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .amount(product.getAmount())
                        .isDiscount(product.getIsDiscount())
                        .discountPrice(product.getDiscountPrice())
                        .imagePath(product.getImagePath())
                        .productCategoryList(product.getProductCategoryList().stream()
                                .map(prodCat -> ProductCategoryResponseDTO.builder()
                                        .categoryId(prodCat.getCategory().getId())
                                        .categoryTitle(prodCat.getCategory().getTitle())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();

        product.setId(UUID.randomUUID().toString());
        product.setTitle(requestDTO.getTitle());
        product.setDescription(requestDTO.getDesc());
        product.setPrice(requestDTO.getPrice());
        product.setAmount(requestDTO.getAmount());
        product.setStock(requestDTO.getStock());
        product.setIsDiscount(requestDTO.getIsDiscount());
        product.setDiscountPrice(requestDTO.getDiscountPrice());

        MultipartFile imageFile = requestDTO.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imagePath = saveImageFile(imageFile);
                product.setImagePath(imagePath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving image file", e);
            }
        }

        List<ProductCategory> productCategories = new ArrayList<>();
        for (ProductCategoryRequestDTO requestCategory : requestDTO.getProductCategoryList()) {
            Category category = categoryRepository.findById(requestCategory.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProduct(product);
            productCategory.setCategory(category);
            productCategories.add(productCategory);
        }

        // Save product first to generate an ID
        productRepository.save(product);

        // Then save product categories
        productCategoryRepository.saveAll(productCategories);

        // Set the categories back to the product
        product.setProductCategoryList(productCategories);

        return ProductResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .amount(product.getAmount())
                .isDiscount(product.getIsDiscount())
                .discountPrice(product.getDiscountPrice())
                .imagePath(product.getImagePath())
                .productCategoryList(productCategories.stream()
                        .map(prodCat -> ProductCategoryResponseDTO.builder()
                                .categoryId(prodCat.getCategory().getId())
                                .categoryTitle(prodCat.getCategory().getTitle())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public ProductResponseDTO getById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Product Was Not Found"));

        return ProductResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .amount(product.getAmount())
                .isDiscount(product.getIsDiscount())
                .discountPrice(product.getDiscountPrice())
                .imagePath(product.getImagePath())
                .productCategoryList(product.getProductCategoryList().stream()
                        .map(prodCat -> ProductCategoryResponseDTO.builder()
                                .categoryId(prodCat.getCategory().getId())
                                .categoryTitle(prodCat.getCategory().getTitle())
                                .build()).collect(Collectors.toList())).build();
    }

    public ProductResponseDTO updateById(String id, ProductRequestDTO request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        product.setTitle(request.getTitle());
        product.setDescription(request.getDesc());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setAmount(request.getAmount());
        product.setIsDiscount(request.getIsDiscount());
        product.setDiscountPrice(request.getDiscountPrice());

        MultipartFile imageFile = request.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imagePath = saveImageFile(imageFile);
                product.setImagePath(imagePath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving image file", e);
            }
        }

        List<ProductCategory> productCategories = new ArrayList<>();
        for (ProductCategoryRequestDTO requestCategory : request.getProductCategoryList()) {
            Category category = categoryRepository.findById(requestCategory.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProduct(product);
            productCategory.setCategory(category);
            productCategories.add(productCategory);
        }

        productRepository.save(product);
        productCategoryRepository.saveAll(productCategories);
        product.setProductCategoryList(productCategories);

        return ProductResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .amount(product.getAmount())
                .isDiscount(product.getIsDiscount())
                .discountPrice(product.getDiscountPrice())
                .imagePath(product.getImagePath())
                .productCategoryList(productCategories.stream()
                        .map(prodCat -> ProductCategoryResponseDTO.builder()
                                .categoryId(prodCat.getCategory().getId())
                                .categoryTitle(prodCat.getCategory().getTitle())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public void deleteProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Product Are not found"));

        productCategoryRepository.deleteByProductId(id);
        productRepository.delete(product);
    }

}
