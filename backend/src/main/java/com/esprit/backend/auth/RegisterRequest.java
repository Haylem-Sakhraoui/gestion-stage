package com.esprit.backend.auth;

import com.esprit.backend.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    public String  studentClass;
    private String firstname ;
    private String lastname ;
    private String email ;
    private String password ;
    private Role role;
    private String token;

}
