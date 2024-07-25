package Ecommerce.Initial_Project.service;

import Ecommerce.Initial_Project.dto.request.CartPaymentRequestDTO;
import Ecommerce.Initial_Project.dto.request.PaymentRequestDTO;
import Ecommerce.Initial_Project.dto.response.*;
import Ecommerce.Initial_Project.model.Cart;
import Ecommerce.Initial_Project.model.CartPayment;
import Ecommerce.Initial_Project.model.Payment;
import Ecommerce.Initial_Project.repository.CartPaymentRepository;
import Ecommerce.Initial_Project.repository.CartRepository;
import Ecommerce.Initial_Project.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CartPaymentRepository cartPaymentRepository;

    @Autowired
    private CartRepository cartRepository;

    public List<PaymentResponseDTO> getAll() {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream().map(payment -> PaymentResponseDTO.builder()
                        .id(payment.getId())
                        .paymentComplete(payment.getPaymentComplete())
                        .cartPaymentResponseList(payment.getCartPaymentList().stream()
                                .map(cartPayment -> CartPaymentResponseDTO.builder()
                                        .cartId(cartPayment.getCart().getId())
                                        .cartList(cartPayment.getCart().getProductList().stream()
                                                .map(productCart -> CartResponseDTO.builder()
                                                        .id(productCart.getCart().getId())
                                                        .totalAmount(productCart.getCart().getAmountTotal())
                                                        .productCartList(productCart.getCart().getProductList().stream()
                                                                .map(productCart1 -> ProductCartResponseDTO.builder()
                                                                        .productId(productCart1.getProduct().getId())
                                                                        .productName(productCart1.getProduct().getTitle())
                                                                        .productPrice(productCart1.getProduct().getPrice())
                                                                        .productAmount(productCart1.getProduct().getAmount())
                                                                        .build())
                                                                .collect(Collectors.toList()))
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public PaymentResponseDTO create(PaymentRequestDTO requestDTO) {
        Payment payment = new Payment();

        payment.setId(UUID.randomUUID().toString());
        payment.setPaymentComplete(requestDTO.getPaymentComplete());

        List<CartPayment> cartPaymentList = new ArrayList<>();
        for (CartPaymentRequestDTO request : requestDTO.getCartPaymentRequestList()) {
            Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart was not founded"));
            CartPayment cartPayment = new CartPayment();
            cartPayment.setCart(cart);
            cartPayment.setPayment(payment);
            cartPaymentList.add(cartPayment);
        }

        paymentRepository.save(payment);

        cartPaymentRepository.saveAll(cartPaymentList);

        payment.setCartPaymentList(cartPaymentList);


        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .paymentComplete(payment.getPaymentComplete())
                .cartPaymentResponseList(payment.getCartPaymentList().stream()
                        .map(cartPayment -> CartPaymentResponseDTO.builder()
                                .cartId(cartPayment.getCart().getId())
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public PaymentResponseDTO getById(String id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment not found"));


        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .paymentComplete(payment.getPaymentComplete())
                .cartPaymentResponseList(payment.getCartPaymentList().stream()
                        .map(cartPayment -> CartPaymentResponseDTO.builder()
                                .cartId(cartPayment.getCart().getId())
                                .cartList(cartPayment.getCart().getProductList().stream()
                                        .map(productCart -> CartResponseDTO.builder()
                                                .id(productCart.getCart().getId())
                                                .totalAmount(productCart.getCart().getAmountTotal())
                                                .productCartList(productCart.getCart().getProductList().stream().map(productCart1 ->
                                                        ProductCartResponseDTO.builder()
                                                                .productId(productCart1.getProduct().getId())
                                                                .productName(productCart1.getProduct().getTitle())
                                                                .productPrice(productCart1.getProduct().getPrice())
                                                                .productAmount(productCart1.getProduct().getAmount())
                                                                .build()).collect(Collectors.toList()))
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public PaymentResponseDTO updateById(String id, PaymentRequestDTO requestDTO) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment not found"));

        payment.setId(requestDTO.getId());
        payment.setPaymentComplete(requestDTO.getPaymentComplete());

        paymentRepository.save(payment);

        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .paymentComplete(payment.getPaymentComplete())
                .build();
    }

    public void delete(String id) {
       Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment not found"));;
        cartPaymentRepository.deleteCartPaymentByid(id);
        paymentRepository.delete(payment);
    }
}
