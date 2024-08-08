package Ecommerce.Initial_Project.service;

import Ecommerce.Initial_Project.dto.request.UserLoginRequestDTO;
import Ecommerce.Initial_Project.dto.request.UserRegisterRequestDTO;
import Ecommerce.Initial_Project.model.User;
import Ecommerce.Initial_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(UserRegisterRequestDTO input) {
        User user = new User();

        user.setUsername(input.getUsername());
        user.setFullname(input.getFullname());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(user);


    }

    public User authenticate(UserLoginRequestDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
