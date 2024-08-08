package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.UserLoginRequestDTO;
import Ecommerce.Initial_Project.dto.request.UserRegisterRequestDTO;
import Ecommerce.Initial_Project.dto.response.LoginResponse;
import Ecommerce.Initial_Project.dto.response.UserLoginResponseDTO;
import Ecommerce.Initial_Project.model.User;
import Ecommerce.Initial_Project.repository.UserRepository;
import Ecommerce.Initial_Project.service.AuthenticationService;
import Ecommerce.Initial_Project.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRegisterRequestDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserLoginRequestDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping(path = "/test")
    public ResponseEntity<?> testData() {
        return ResponseEntity.ok("Custom message: test successful");
    }
}
