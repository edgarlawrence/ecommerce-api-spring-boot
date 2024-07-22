package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.CategoryRequestDTO;
import Ecommerce.Initial_Project.dto.response.CategoryResponseDTO;
import Ecommerce.Initial_Project.dto.response.CategoryResponseDTO;
import Ecommerce.Initial_Project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/api/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        var responses = categoryService.getAll();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping(path = "/api/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO request) {
        var responses = categoryService.create(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(path = "/api/category/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable("id") String id) {
        var responses = categoryService.getById(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping(path = "/api/category/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable("id") String id, @RequestBody CategoryResponseDTO request) {
        var responses = categoryService.updateById(id, request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        categoryService.delete(id);
        return new ResponseEntity<>("Category has been deleted", HttpStatus.OK);
    }
}
