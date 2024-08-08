package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.CategoryRequestDTO;
import Ecommerce.Initial_Project.dto.request.ProductRequestDTO;
import Ecommerce.Initial_Project.dto.response.CategoryResponseDTO;
import Ecommerce.Initial_Project.dto.response.ProductResponseDTO;
import Ecommerce.Initial_Project.service.ProductService;
import Ecommerce.Initial_Project.util.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private JwtService jwtService;

    @GetMapping(path = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> getAll(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            // Validate the token (optional)
            if (jwtService.isTokenValid(token)) {
                // Token is valid; you can perform any additional logic if needed
            } else {
                // Token is invalid; you might want to log this or handle it differently
            }
        }

        // Fetch and return the product data regardless of token presence/validity
        var responses = productService.getAll();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping(path = "/api/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> create(@RequestPart("product") String productJson,
                                                     @RequestPart("image") MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDTO request = objectMapper.readValue(productJson, ProductRequestDTO.class);
        request.setImage(imageFile);
        ProductResponseDTO response = productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/api/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable("id") String id) {
        var responses = productService.getById(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping(path = "/api/product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> updateById(@PathVariable("id") String id,
                                                         @RequestPart("product") String productJson,
                                                         @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductRequestDTO request = objectMapper.readValue(productJson, ProductRequestDTO.class);
            request.setImage(imageFile);
            ProductResponseDTO response = productService.updateById(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product data", e);
        }
    }

    @DeleteMapping(path = "/api/product/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product Has been deleted", HttpStatus.GONE);
    }
}
