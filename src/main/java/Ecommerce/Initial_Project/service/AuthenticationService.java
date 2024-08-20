package Ecommerce.Initial_Project.service;

import Ecommerce.Initial_Project.dto.request.UserLoginRequestDTO;
import Ecommerce.Initial_Project.dto.request.UserRegisterRequestDTO;
import Ecommerce.Initial_Project.dto.request.UserRoleRequestDTO;
import Ecommerce.Initial_Project.dto.response.RoleResponseDTO;
import Ecommerce.Initial_Project.dto.response.UserRoleResponseDTO;
import Ecommerce.Initial_Project.dto.response.UserSignUpResponseDTO;
import Ecommerce.Initial_Project.model.Role;
import Ecommerce.Initial_Project.model.User;
import Ecommerce.Initial_Project.model.UserRole;
import Ecommerce.Initial_Project.repository.RoleRepository;
import Ecommerce.Initial_Project.repository.UserRepository;
import Ecommerce.Initial_Project.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    public UserSignUpResponseDTO signup(UserRegisterRequestDTO input) {
        var user = new User();
        user.setFullname(input.getFullname());
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        List<UserRole> userRoleList = new ArrayList<>();
        for (UserRoleRequestDTO requestDTO : input.getUserRoleRequestDTOList()) {
            Role role = roleRepository.findById(requestDTO.getRoleId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            userRole.setIsActive(true);
            userRoleList.add(userRole);
        }

        // Save the user first to ensure the User is persisted with an ID
        userRepository.save(user);

        // Save the UserRole relationships
        userRoleRepository.saveAll(userRoleList);

        // Attach the saved user roles to the user entity
        user.setUserRoleList(userRoleList);

        // Return UserSignUpResponseDTO with full details
        return UserSignUpResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .userRoleList(userRoleList.stream().map(userRole -> UserRoleResponseDTO.builder()
                        .roleId(userRole.getRole().getId()) // Get the Role ID from the Role entity
                        .roleCode(userRole.getRole().getRoleCode()) // Get Role Code
                        .roleDescription(userRole.getRole().getRoleDescription()) // Get Role Description
                        .isActive(userRole.getIsActive()) // Get if the role is active
                        .build()).collect(Collectors.toList()))
                .build();
    }

    public User authenticate(UserLoginRequestDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + input.getEmail()));
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
