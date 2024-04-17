package com.esprit.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String studentClass;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    @OneToOne
    private Token token;

    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean enabled = false;

  @OneToMany(mappedBy="user",cascade={CascadeType.ALL})
  private Set<Offre> offres;

  @OneToOne( cascade = CascadeType.ALL, mappedBy = "user")
  private CvStage cvStage;
    @OneToMany(mappedBy="user",cascade={CascadeType.ALL})
    private Set<Reclamation> reclamation;





/*
=======
>>>>>>> 282da086cf69489b764bb08939a501c01811c706
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
<<<<<<< HEAD
  @OneToMany(mappedBy="user",cascade={CascadeType.ALL})
  private Set<Vote> votes;
=======

>>>>>>> ae9697aeb5d34a336a0d9b34113ce0f9a8eb9262
}
