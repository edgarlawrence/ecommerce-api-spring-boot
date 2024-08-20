package Ecommerce.Initial_Project.controller;

import Ecommerce.Initial_Project.dto.request.UserLoginRequestDTO;
import Ecommerce.Initial_Project.dto.request.UserRegisterRequestDTO;
import Ecommerce.Initial_Project.dto.response.LoginResponse;
import Ecommerce.Initial_Project.dto.response.UserSignUpResponseDTO;
import Ecommerce.Initial_Project.model.User;
import Ecommerce.Initial_Project.service.AuthenticationService;
import Ecommerce.Initial_Project.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDTO> register(@RequestBody UserRegisterRequestDTO registerUserDto) {
        var registeredUser = authenticationService.signup(registerUserDto);

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
}
