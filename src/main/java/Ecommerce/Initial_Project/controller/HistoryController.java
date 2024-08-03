package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.HistoryRequestDTO;
import Ecommerce.Initial_Project.dto.request.PaymentRequestDTO;
import Ecommerce.Initial_Project.dto.request.ProductRequestDTO;
import Ecommerce.Initial_Project.dto.response.HistoryResponseDTO;
import Ecommerce.Initial_Project.dto.response.PaymentResponseDTO;
import Ecommerce.Initial_Project.dto.response.ProductResponseDTO;
import Ecommerce.Initial_Project.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping(path = "/api/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HistoryResponseDTO>> getAll() {
        var responses = historyService.getAllData();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping(path = "/api/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HistoryResponseDTO> create(@RequestBody HistoryRequestDTO request) {
        var responses = historyService.create(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(path = "/api/history/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HistoryResponseDTO> getById(@PathVariable("id") String id) {
        var responses = historyService.getById(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping(path = "/api/history/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HistoryResponseDTO> updateById(@PathVariable("id") String  id, @RequestBody HistoryRequestDTO requestDTO) {
            HistoryResponseDTO response = historyService.updateById(id, requestDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/history/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        historyService.delete(id);
        return new ResponseEntity<>("Product Has been deleted", HttpStatus.GONE);
    }
}
