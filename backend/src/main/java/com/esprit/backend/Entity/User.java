package com.esprit.backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    private String studentClass;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy="user",cascade={CascadeType.ALL})
    private Set<Reclamation> reclamation;

/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }*/
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    // Convertir le rôle de l'utilisateur en GrantedAuthority
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name()));

}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
