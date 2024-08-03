package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.CartRequestDTO;
import Ecommerce.Initial_Project.dto.request.PaymentRequestDTO;
import Ecommerce.Initial_Project.dto.response.CartResponseDTO;
import Ecommerce.Initial_Project.dto.response.PaymentResponseDTO;
import Ecommerce.Initial_Project.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping(path = "/api/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentResponseDTO>> getAll() {
        var responses = paymentService.getAll();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping(path = "/api/payment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> create(@RequestPart("payment") String request,
                                                     @RequestPart("image")MultipartFile imageFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentRequestDTO paymentRequest = objectMapper.readValue(request, PaymentRequestDTO.class);
        paymentRequest.setImage(imageFile);
        var responses = paymentService.create(paymentRequest);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(path = "/api/payment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable("id") String id) {
        var responses = paymentService.getById(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping(path = "/api/payment/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDTO> updateById(@PathVariable("id") String id, @RequestBody PaymentRequestDTO requestDTO) {
        var responses = paymentService.updateById(id, requestDTO);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/payment/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        paymentService.delete(id);
        return new ResponseEntity<>("Cart Has been deleted", HttpStatus.GONE);
    }
}
