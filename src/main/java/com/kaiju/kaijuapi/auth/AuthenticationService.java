package com.kaiju.kaijuapi.auth;

import com.kaiju.kaijuapi.config.JwtService;
import com.kaiju.kaijuapi.entity.Role;
import com.kaiju.kaijuapi.entity.User;
import com.kaiju.kaijuapi.repository.RoleRepository;
import com.kaiju.kaijuapi.repository.UserRepository;
import com.kaiju.kaijuapi.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    public AuthenticationResponse signup(SignUpRequest request) {
            Role userRole = roleRepository.findByAuthority("USER").get();
            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);

        System.out.println("SignUpRequest request: " + request);
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .authorities(authorities)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .user(user)
                .jwt(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try{
            System.out.println("Inside AuthenticationService authenticate request: " + request);
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            //Authentication successful, proceeding with retrieving user details
            var user = userRepository.findByUsername(request.getEmail())
                    .orElseThrow();
            System.out.println("Found user: " + user);
            var jwtToken = tokenService.generateToken(auth);
            System.out.println("Generated jwtToken: " +  jwtToken);
            return AuthenticationResponse.builder()
                    .user(user)
                    .jwt(jwtToken)
                    .build();
        } catch(AuthenticationException e) {
            //Authentication failed
            throw new UsernameNotFoundException("Invalid email/password supplied");
        }

    }
}
