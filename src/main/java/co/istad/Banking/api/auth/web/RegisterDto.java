package co.istad.Banking.api.auth.web;


import co.istad.Banking.api.user.validator.email.EmailUnique;
import co.istad.Banking.api.user.validator.password.Password;
import co.istad.Banking.api.user.validator.password.PasswordMatch;
import co.istad.Banking.api.user.validator.role.RoleIdConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
@PasswordMatch(message= "Your password is not Match",password = "password" ,confirmedPassword = "confirmedPassword" )
public record RegisterDto(
        @NotBlank(message = "Email is required")
        @EmailUnique
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Password
        String password,
        @NotBlank(message = "ConfirmedPassword is required")
        @Password
        String confirmedPassword,

        @NotNull(message = "Roles are required !")
        @RoleIdConstraint
        List<Integer> roleIds) {
}
