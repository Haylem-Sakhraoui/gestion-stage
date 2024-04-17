package com.esprit.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



@RequiredArgsConstructor
public enum Role {
    ADMIN, SERVICESTAGE,STUDENT, SUPERVISOR;

}
