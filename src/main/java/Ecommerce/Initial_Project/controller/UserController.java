package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.model.User;
import Ecommerce.Initial_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        System.out.println("Layer 1");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Layer 2");
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Authentication is null or not authenticated.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("Layer 3");
        System.out.println("Authentication: " + authentication);

        Object principal = authentication.getPrincipal();
        System.out.println("Principal: " + principal);

        if (principal instanceof User) {
            User currentUser = (User) principal;
            System.out.println("User found: " + currentUser);
            return ResponseEntity.ok(currentUser);
        } else if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) principal;
            System.out.println("UserDetails found: " + userDetails.getUsername());
            return ResponseEntity.ok(userDetails.getUsername());
        } else {
            System.out.println("Invalid principal type: " + principal.getClass().getName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid principal type: " + principal.getClass().getName());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}
