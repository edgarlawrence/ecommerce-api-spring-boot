package Ecommerce.Initial_Project.service;

import Ecommerce.Initial_Project.dto.request.CartRequestDTO;
import Ecommerce.Initial_Project.dto.request.ProductCartRequestDTO;
import Ecommerce.Initial_Project.dto.response.CartResponseDTO;
import Ecommerce.Initial_Project.dto.response.ProductCartResponseDTO;
import Ecommerce.Initial_Project.model.Cart;
import Ecommerce.Initial_Project.model.Product;
import Ecommerce.Initial_Project.model.ProductCart;
import Ecommerce.Initial_Project.repository.CartRepository;
import Ecommerce.Initial_Project.repository.ProductCartRepository;
import Ecommerce.Initial_Project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductCartRepository productCartRepository;

    public List<CartResponseDTO> getAll() {
        List<Cart> carts = cartRepository.findAll();

       return carts.stream().map(cart -> CartResponseDTO.builder()
               .id(cart.getId())
               .totalAmount(cart.getAmountTotal())
               .productCartList(cart.getProductList().stream()
                       .map(prodCart -> ProductCartResponseDTO.builder()
                       .productName(prodCart.getProduct().getTitle())
                               .productId(prodCart.getProduct().getId())
                               .productName(prodCart.getProduct().getTitle())
                               .productPrice(prodCart.getProduct().getPrice())
                               .productAmount(prodCart.getProduct().getAmount())
                       .build()).collect(Collectors.toList()))
               .build()).collect(Collectors.toList());
    }

    public CartResponseDTO create(CartRequestDTO requestDTO) {
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setAmountTotal(0L);

        List<ProductCart> productCartInit = new ArrayList<>();
        long totalAmount = 0L;

        for (ProductCartRequestDTO request : requestDTO.getProductCartList()) {
            Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
            ProductCart productCarts = new ProductCart();
            productCarts.setProduct(product);
            productCarts.setCart(cart);
            productCartInit.add(productCarts);

            totalAmount += product.getPrice();
        }

        cart.setAmountTotal(totalAmount);
        cart.setProductList(productCartInit);
        cartRepository.save(cart);
        productCartRepository.saveAll(productCartInit);

        return CartResponseDTO.builder()
                .id(cart.getId())
                .totalAmount(cart.getAmountTotal())
                .productCartList(productCartInit.stream().map(productCart -> (
                    ProductCartResponseDTO.builder()
                            .productId(productCart.getProduct().getId())
                            .productName(productCart.getProduct().getTitle())
                            .productPrice(productCart.getProduct().getPrice())
                            .productAmount(productCart.getProduct().getAmount())
                            .build()
                )).collect(Collectors.toList()))
                .build();
    }

    public CartResponseDTO getById(String id) {
        Cart carts = cartRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart was not found"));

        return CartResponseDTO.builder()
                .id(carts.getId())
                .totalAmount(carts.getAmountTotal())
                .productCartList(carts.getProductList().stream().map(productCart ->
                        ProductCartResponseDTO.builder()
                                .productId(productCart.getProduct().getId())
                                .productName(productCart.getProduct().getTitle())
                                .productPrice(productCart.getProduct().getPrice())
                                .productAmount(productCart.getProduct().getAmount())
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public CartResponseDTO updateById(String id, CartRequestDTO requestDTO) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart was not found"));

        // Clear the existing product list
        cart.getProductList().clear();

        List<ProductCart> productCartInit = new ArrayList<>();
        long totalAmount = 0L;

        for (ProductCartRequestDTO request : requestDTO.getProductCartList()) {
            Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
            ProductCart productCarts = new ProductCart();
            productCarts.setProduct(product);
            productCarts.setCart(cart);
            productCartInit.add(productCarts);

            totalAmount += product.getPrice();
        }

        cart.setAmountTotal(totalAmount);
        cartRepository.save(cart);
        productCartRepository.saveAll(productCartInit);
        cart.getProductList().addAll(productCartInit);

        return CartResponseDTO.builder()
                .id(cart.getId())
                .totalAmount(cart.getAmountTotal())
                .productCartList(productCartInit.stream().map(productCart -> (
                        ProductCartResponseDTO.builder()
                                .productId(productCart.getProduct().getId())
                                .productName(productCart.getProduct().getTitle())
                                .productPrice(productCart.getProduct().getPrice())
                                .productAmount(productCart.getProduct().getAmount())
                                .build()
                )).collect(Collectors.toList()))
                .build();
    }

    public void delete(String id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart was not found"));

        productCartRepository.deleteByCartId(id);
        cartRepository.delete(cart);
    }
}
