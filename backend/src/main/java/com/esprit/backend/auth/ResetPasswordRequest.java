package com.esprit.backend.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

}
