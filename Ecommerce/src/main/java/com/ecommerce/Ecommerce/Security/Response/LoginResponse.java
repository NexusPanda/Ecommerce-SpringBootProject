package com.ecommerce.Ecommerce.Security.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoginResponse {

    private Long id;

    private String username;

    private String jwtToken;

    private List<String> roles;

    public LoginResponse(Long id, String username, String jwtToken, List<String> roles) {
        this.id = id;
        this.username = username;
        this.jwtToken = jwtToken;
        this.roles = roles;
    }

}
