package com.esprit.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.esprit.backend.Entity.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    SERVICESTAGE_READ,
                    SERVICESTAGE_UPDATE,
                    SERVICESTAGE_CREATE,
                    SERVICESTAGE_DELETE,
                    SUPERVISOR_READ,
                    SUPERVISOR_UPDATE,
                    SUPERVISOR_DELETE,
                    SUPERVISOR_CREATE,
                    STUDENT_CREATE,
                    STUDENT_READ,
                    STUDENT_UPDATE,
                    STUDENT_DELETE
            )
    ), SERVICESTAGE(
            Set.of(
                    SERVICESTAGE_READ,
                    SERVICESTAGE_UPDATE,
                    SERVICESTAGE_CREATE,
                    SERVICESTAGE_DELETE,
                    STUDENT_CREATE,
                    STUDENT_READ,
                    STUDENT_UPDATE,
                    STUDENT_DELETE,
                    SUPERVISOR_READ,
                    SUPERVISOR_UPDATE,
                    SUPERVISOR_DELETE,
                    SUPERVISOR_CREATE
            )
    ) ,STUDENT(
            Set.of(
                    STUDENT_CREATE,
                    STUDENT_READ,
                    STUDENT_UPDATE,
                    STUDENT_DELETE

            )
    ), SUPERVISOR(
            Set.of(
                    SUPERVISOR_READ,
                    SUPERVISOR_UPDATE,
                    SUPERVISOR_CREATE,
                    SUPERVISOR_DELETE,
                    STUDENT_CREATE,
                    STUDENT_READ,
                    STUDENT_UPDATE,
                    STUDENT_DELETE

            )
    );
    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
