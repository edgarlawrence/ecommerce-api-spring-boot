package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.RoleRequestDTO;
import Ecommerce.Initial_Project.dto.response.RoleResponseDTO;
import Ecommerce.Initial_Project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAll() {
        var roles = roleService.getAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO> create(@RequestBody RoleRequestDTO requestDTO) {
        var roles = roleService.create(requestDTO);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RoleResponseDTO> findById(@PathVariable Integer id) {
        var roles = roleService.findById(id);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RoleResponseDTO> updateByid(@PathVariable Integer id, @RequestBody RoleRequestDTO requestDTO) {
        var roles = roleService.updateById(id, requestDTO);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return new ResponseEntity<>("Role Has been Deleted", HttpStatus.OK);
    }
}
