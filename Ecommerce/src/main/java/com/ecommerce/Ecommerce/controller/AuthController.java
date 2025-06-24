package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.Security.Jwt.JwtUtils;
import com.ecommerce.Ecommerce.Security.Request.LoginRequest;
import com.ecommerce.Ecommerce.Security.Response.LoginResponse;
import com.ecommerce.Ecommerce.SecurityService.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.getTokenFromUserName(userDetails);

        return ResponseEntity.ok(new LoginResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                jwtToken,
                userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .toList()
        ));
    }
}
