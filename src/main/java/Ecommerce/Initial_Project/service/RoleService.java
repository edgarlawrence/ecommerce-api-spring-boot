package Ecommerce.Initial_Project.service;

import Ecommerce.Initial_Project.dto.request.RoleRequestDTO;
import Ecommerce.Initial_Project.dto.response.RoleResponseDTO;
import Ecommerce.Initial_Project.model.Role;
import Ecommerce.Initial_Project.repository.RoleRepository;
import Ecommerce.Initial_Project.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<RoleResponseDTO> getAll() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream().map(role -> RoleResponseDTO.builder()
                .id(role.getId())
                .roleCode(role.getRoleCode())
                .roleDescription(role.getRoleDescription()).build()).collect(Collectors.toList());

    }

    public RoleResponseDTO create(RoleRequestDTO requestDTO) {
        Role role = new Role();
        role.setRoleCode(requestDTO.getRoleCode());
        role.setRoleDescription(requestDTO.getRoleDescription());

        roleRepository.save(role);

        return RoleResponseDTO.builder()
                .id(role.getId())
                .roleCode(role.getRoleCode())
                .roleDescription(role.getRoleDescription())
                .build();
    }

    public RoleResponseDTO findById(Integer roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Was Not Found"));

        return RoleResponseDTO.builder()
                .id(role.getId())
                .roleCode(role.getRoleCode())
                .roleDescription(role.getRoleDescription())
                .build();
    }

    public RoleResponseDTO updateById(Integer roleId, RoleRequestDTO requestDTO) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Was Not Found"));

        role.setRoleCode(requestDTO.getRoleCode());
        role.setRoleDescription(requestDTO.getRoleDescription());

        roleRepository.save(role);

        return RoleResponseDTO.builder()
                .id(role.getId())
                .roleCode(role.getRoleCode())
                .roleDescription(role.getRoleDescription())
                .build();
    }

    public void delete(Integer roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Was Not Found"));

        roleRepository.delete(role);
    }
}
