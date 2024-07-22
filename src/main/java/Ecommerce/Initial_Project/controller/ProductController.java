package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.CategoryRequestDTO;
import Ecommerce.Initial_Project.dto.request.ProductRequestDTO;
import Ecommerce.Initial_Project.dto.response.CategoryResponseDTO;
import Ecommerce.Initial_Project.dto.response.ProductResponseDTO;
import Ecommerce.Initial_Project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/api/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        var responses = productService.getAll();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping(path = "/api/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO request) {
        var responses = productService.createProduct(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(path = "/api/product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable("id") String id) {
        var responses = productService.getById(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping(path = "/api/product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDTO> updateById(@PathVariable("id") String id, @RequestBody ProductRequestDTO requestDTO) {
        var responses = productService.updateById(id, requestDTO);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product Has been deleted", HttpStatus.GONE);
    }
}
