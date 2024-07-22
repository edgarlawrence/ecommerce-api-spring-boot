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
import org.springframework.web.server.ResponseStatusException;

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

    public List<ProductResponseDTO> getAll() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> ProductResponseDTO.builder()
                        .id(product.getId())
                        .title(product.getTitle())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .isDiscount(product.getIsDiscount())
                        .discountPrice(product.getDiscountPrice())
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
        product.setStock(requestDTO.getStock());
        product.setIsDiscount(requestDTO.getIsDiscount());
        product.setDiscountPrice(requestDTO.getDiscountPrice());

        List<ProductCategory> productCategories = new ArrayList<>();
        for(ProductCategoryRequestDTO requestCategory : requestDTO.getProductCategoryList()) {
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
                .isDiscount(product.getIsDiscount())
                .discountPrice(product.getDiscountPrice())
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
                .isDiscount(product.getIsDiscount())
                .discountPrice(product.getDiscountPrice())
                .productCategoryList(product.getProductCategoryList().stream()
                        .map(prodCat -> ProductCategoryResponseDTO.builder()
                                .categoryId(prodCat.getCategory().getId())
                                .categoryTitle(prodCat.getCategory().getTitle())
                                .build()).collect(Collectors.toList())).build();
    }

    public ProductResponseDTO updateById(String id, ProductRequestDTO request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.OK, "Product Not Found"));

        product.setId(request.getId());
        product.setTitle(request.getTitle());
        product.setDescription(request.getDesc());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setIsDiscount(request.getIsDiscount());
        product.setDiscountPrice(request.getDiscountPrice());

        List<ProductCategory> productCategories = new ArrayList<>();

        for (ProductCategoryRequestDTO requestDTO : request.getProductCategoryList()) {
            Category category = categoryRepository.findById(requestDTO.getCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
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
                .isDiscount(product.getIsDiscount())
                .discountPrice(product.getDiscountPrice())
                .productCategoryList(productCategories.stream()
                        .map(prodCat ->  ProductCategoryResponseDTO.builder()
                                .categoryId(prodCat.getCategory().getId())
                                .categoryTitle(prodCat.getCategory().getTitle())
                                .build()).collect(Collectors.toList())).build();
    }

    public void deleteProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Product Are not found"));

        productCategoryRepository.deleteByProductId(id);
        productRepository.delete(product);
    }

}
