package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.response.UserRoleResponseDTO;
import Ecommerce.Initial_Project.dto.response.UserSignUpResponseDTO;
import Ecommerce.Initial_Project.model.User;
import Ecommerce.Initial_Project.repository.UserRepository;
import Ecommerce.Initial_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (authentication.getAuthorities().stream().anyMatch(auth ->
                auth.getAuthority().equals("ROLE_SA") ||
                        auth.getAuthority().equals("ROLE_SUPER_ADMIN") ||
                        auth.getAuthority().equals("ROLE_ADM") ||
                        auth.getAuthority().equals("ROLE_ADMIN"))) {

            Object principal = authentication.getPrincipal();

            // Handle different principal types
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                // Case where principal is an instance of org.springframework.security.core.userdetails.User
                String usernameOrEmail = ((org.springframework.security.core.userdetails.User) principal).getUsername();
                User currentUser = userRepository.findByUsername(usernameOrEmail)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
                return ResponseEntity.ok(currentUser);

            } else if (principal instanceof User) {
                // Case where principal is the custom User entity from your database
                User currentUser = (User) principal;
                var currentUserRes = UserSignUpResponseDTO.builder()
                        .id(currentUser.getId())
                        .email(currentUser.getEmail())
                        .username(currentUser.getUsername())
                        .userRoleList(currentUser.getUserRoleList().stream()
                                .map(userRole -> UserRoleResponseDTO.builder()
                                        .roleId(userRole.getRole().getId())
                                        .roleCode(userRole.getRole().getRoleCode())
                                        .roleDescription(userRole.getRole().getRoleDescription())
                                        .isActive(userRole.getIsActive())
                                        .build()).collect(Collectors.toList()))
                        .build();
                return ResponseEntity.ok(currentUserRes);

            } else if (principal instanceof String) {
                // This could happen if only a username or email is stored as the principal
                String usernameOrEmail = (String) principal;
                User currentUser = userRepository.findByUsername(usernameOrEmail)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
                return ResponseEntity.ok(currentUser);

            } else {
                // Unrecognized principal type, return an unauthorized response
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid principal type: " + principal.getClass().getName());
            }

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}
