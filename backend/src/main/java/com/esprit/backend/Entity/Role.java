package com.esprit.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

<<<<<<< HEAD
=======
import static com.esprit.backend.Entity.Permission.*;

>>>>>>> ae9697aeb5d34a336a0d9b34113ce0f9a8eb9262


@RequiredArgsConstructor
public enum Role {
    ADMIN, SERVICESTAGE,STUDENT, SUPERVISOR;

}
