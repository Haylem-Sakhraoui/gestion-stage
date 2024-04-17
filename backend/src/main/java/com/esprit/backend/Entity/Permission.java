package com.esprit.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    SERVICESTAGE_READ("servicestage:read"),
    SERVICESTAGE_UPDATE("servicestage:update"),
    SERVICESTAGE_CREATE("servicestage:create"),
    SERVICESTAGE_DELETE("servicestage:delete"),
    SUPERVISOR_READ("supervisor:read"),
    SUPERVISOR_UPDATE("supervisor :update"),
    SUPERVISOR_CREATE("supervisor :create"),
    SUPERVISOR_DELETE("supervisor :delete"),
    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student :update"),
    STUDENT_CREATE("student :create"),
    STUDENT_DELETE("student :delete")

            ;

    @Getter
    private final String permission;
}
