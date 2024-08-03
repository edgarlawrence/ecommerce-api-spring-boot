package Ecommerce.Initial_Project.service;

import Ecommerce.Initial_Project.dto.request.HistoryRequestDTO;
import Ecommerce.Initial_Project.dto.request.PaymentHistoryRequestDTO;
import Ecommerce.Initial_Project.dto.response.*;
import Ecommerce.Initial_Project.model.History;
import Ecommerce.Initial_Project.model.Payment;
import Ecommerce.Initial_Project.model.PaymentHistory;
import Ecommerce.Initial_Project.repository.HistoryRepository;
import Ecommerce.Initial_Project.repository.PaymentHistoryRepository;
import Ecommerce.Initial_Project.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<HistoryResponseDTO> getAllData() {
        List<History> histories = historyRepository.findAll();

        return histories.stream().map(history ->
                HistoryResponseDTO.builder()
                        .id(history.getId().toString())
                        .paymentList(history.getPaymentHistoryList().stream().map(
                                paymentHistory -> PaymentHistoryResponseDTO.builder()
                                        .paymentId(paymentHistory.getId().toString())
                                        .paymentList(paymentHistory.getPayment().getCartPaymentList().stream().map(
                                                payment -> PaymentResponseDTO.builder()
                                                        .id(payment.getPayment().getId())
                                                        .paymentComplete(payment.getPayment().getPaymentComplete())
                                                        .image(payment.getPayment().getImagePath())
                                                        .cartPaymentResponseList(payment.getPayment().getCartPaymentList().stream()
                                                                .map(paymentCart -> CartPaymentResponseDTO.builder()
                                                                        .cartId(paymentCart.getCart().getId())
                                                                        .cartList(paymentCart.getCart().getProductList().stream()
                                                                                .map(productCart -> CartResponseDTO.builder()
                                                                                        .id(productCart.getCart().getId())
                                                                                        .totalAmount(productCart.getCart().getAmountTotal())
                                                                                        .productCartList(productCart.getProduct().getProductCartList().stream()
                                                                                                .map(productCartNest -> ProductCartResponseDTO.builder()
                                                                                                        .productId(productCartNest.getProduct().getId())
                                                                                                        .productName(productCartNest.getProduct().getTitle())
                                                                                                        .productAmount(productCartNest.getProduct().getAmount())
                                                                                                        .build()).collect(Collectors.toList()))
                                                                                        .build()).collect(Collectors.toList()))
                                                                        .build()).collect(Collectors.toList()))
                                                        .build()).collect(Collectors.toList()))
                                        .build()).collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());
    }


    public HistoryResponseDTO create(HistoryRequestDTO requestDTO) {
        History history = new History();

        List<PaymentHistory> paymentHistoryArray = new ArrayList<>();
        for (PaymentHistoryRequestDTO request : requestDTO.getPaymentHistoryList()) {
            Payment payment = paymentRepository.findById(request.getPaymentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setId(history.getId());
            paymentHistory.setHistory(history);
            paymentHistory.setPayment(payment);
            paymentHistoryArray.add(paymentHistory);
        }

        historyRepository.save(history);
        paymentHistoryRepository.saveAll(paymentHistoryArray);
        history.setPaymentHistoryList(paymentHistoryArray);

        return HistoryResponseDTO.builder()
                .id(history.getId().toString())
                .paymentList(history.getPaymentHistoryList().stream()
                        .map(paymentHistory -> PaymentHistoryResponseDTO.builder()
                                .paymentId(paymentHistory.getId().toString())
                                .paymentList(paymentHistory.getPayment().getCartPaymentList().stream()
                                        .map(payments -> PaymentResponseDTO.builder()
                                                .id(payments.getPayment().getId())
                                                .paymentComplete(payments.getPayment().getPaymentComplete())
                                                .image(payments.getPayment().getImagePath())
                                                .cartPaymentResponseList(payments.getPayment().getCartPaymentList().stream()
                                                        .map(paymentCart -> CartPaymentResponseDTO.builder()
                                                                .cartId(paymentCart.getCart().getId())
                                                                .cartList(paymentCart.getCart().getProductList().stream()
                                                                        .map(productCart -> CartResponseDTO.builder()
                                                                                .id(productCart.getCart().getId())
                                                                                .totalAmount(productCart.getCart().getAmountTotal())
                                                                                .productCartList(productCart.getProduct().getProductCartList().stream()
                                                                                        .map(productCartNest -> ProductCartResponseDTO.builder()
                                                                                                .productId(productCartNest.getProduct().getId())
                                                                                                .productName(productCartNest.getProduct().getTitle())
                                                                                                .productAmount(productCartNest.getProduct().getAmount())
                                                                                                .build()).collect(Collectors.toList()))
                                                                        .build()).collect(Collectors.toList()))
                                                                .build()).collect(Collectors.toList()))
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public HistoryResponseDTO getById(String id) {
        History history = historyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return HistoryResponseDTO.builder()
                .id(history.getId().toString())
                .paymentList(history.getPaymentHistoryList().stream()
                        .map(paymentHistory -> PaymentHistoryResponseDTO.builder()
                                .paymentId(paymentHistory.getId().toString())
                                .paymentList(paymentHistory.getPayment().getCartPaymentList().stream()
                                        .map(payments -> PaymentResponseDTO.builder()
                                                .id(payments.getPayment().getId())
                                                .paymentComplete(payments.getPayment().getPaymentComplete())
                                                .image(payments.getPayment().getImagePath())
                                                .cartPaymentResponseList(payments.getPayment().getCartPaymentList().stream()
                                                        .map(paymentCart -> CartPaymentResponseDTO.builder()
                                                                .cartId(paymentCart.getCart().getId())
                                                                .cartList(paymentCart.getCart().getProductList().stream()
                                                                        .map(productCart -> CartResponseDTO.builder()
                                                                                .id(productCart.getCart().getId())
                                                                                .totalAmount(productCart.getCart().getAmountTotal())
                                                                                .productCartList(productCart.getProduct().getProductCartList().stream()
                                                                                        .map(productCartNest -> ProductCartResponseDTO.builder()
                                                                                                .productId(productCartNest.getProduct().getId())
                                                                                                .productName(productCartNest.getProduct().getTitle())
                                                                                                .productAmount(productCartNest.getProduct().getAmount())
                                                                                                .build()).collect(Collectors.toList()))
                                                                                .build()).collect(Collectors.toList()))
                                                                .build()).collect(Collectors.toList()))
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public HistoryResponseDTO updateById(String id, HistoryRequestDTO requestDTO) {
        History history = historyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<PaymentHistory> paymentHistoryArray = new ArrayList<>();
        for (PaymentHistoryRequestDTO request : requestDTO.getPaymentHistoryList()) {
            Payment payment = paymentRepository.findById(request.getPaymentId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setId(history.getId());
            paymentHistory.setHistory(history);
            paymentHistory.setPayment(payment);
            paymentHistoryArray.add(paymentHistory);
        }

        historyRepository.save(history);
        paymentHistoryRepository.saveAll(paymentHistoryArray);
        history.setPaymentHistoryList(paymentHistoryArray);

        return HistoryResponseDTO.builder()
                .id(history.getId().toString())
                .paymentList(history.getPaymentHistoryList().stream()
                        .map(paymentHistory -> PaymentHistoryResponseDTO.builder()
                                .paymentId(paymentHistory.getId().toString())
                                .paymentList(paymentHistory.getPayment().getCartPaymentList().stream()
                                        .map(payments -> PaymentResponseDTO.builder()
                                                .id(payments.getPayment().getId())
                                                .paymentComplete(payments.getPayment().getPaymentComplete())
                                                .image(payments.getPayment().getImagePath())
                                                .cartPaymentResponseList(payments.getPayment().getCartPaymentList().stream()
                                                        .map(paymentCart -> CartPaymentResponseDTO.builder()
                                                                .cartId(paymentCart.getCart().getId())
                                                                .cartList(paymentCart.getCart().getProductList().stream()
                                                                        .map(productCart -> CartResponseDTO.builder()
                                                                                .id(productCart.getCart().getId())
                                                                                .totalAmount(productCart.getCart().getAmountTotal())
                                                                                .productCartList(productCart.getProduct().getProductCartList().stream()
                                                                                        .map(productCartNest -> ProductCartResponseDTO.builder()
                                                                                                .productId(productCartNest.getProduct().getId())
                                                                                                .productName(productCartNest.getProduct().getTitle())
                                                                                                .productAmount(productCartNest.getProduct().getAmount())
                                                                                                .build()).collect(Collectors.toList()))
                                                                                .build()).collect(Collectors.toList()))
                                                                .build()).collect(Collectors.toList()))
                                                .build()).collect(Collectors.toList()))
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public void delete(String id) {
        History history = historyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        paymentHistoryRepository.deletePaymentHistoryById(id);
        historyRepository.delete(history);
    }

}
