package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.CartRequestDTO;
import Ecommerce.Initial_Project.dto.request.CategoryRequestDTO;
import Ecommerce.Initial_Project.dto.request.ProductRequestDTO;
import Ecommerce.Initial_Project.dto.response.CartResponseDTO;
import Ecommerce.Initial_Project.dto.response.CategoryResponseDTO;
import Ecommerce.Initial_Project.dto.response.ProductResponseDTO;
import Ecommerce.Initial_Project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping(path = "/api/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CartResponseDTO>> getAll() {
        var responses = cartService.getAll();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping(path = "/api/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResponseDTO> create(@RequestBody CartRequestDTO request) {
        var responses = cartService.create(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(path = "/api/cart/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResponseDTO> getById(@PathVariable("id") String id) {
        var responses = cartService.getById(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping(path = "/api/cart/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResponseDTO> updateById(@PathVariable("id") String id, @RequestBody CartRequestDTO requestDTO) {
        var responses = cartService.updateById(id, requestDTO);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/cart/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        cartService.delete(id);
        return new ResponseEntity<>("Cart Has been deleted", HttpStatus.GONE);
    }
}
