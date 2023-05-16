package co.istad.Banking.api.auth.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record RegisterDto(
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "ConfirmedPassword is required")
        String confirmedPassword,

        @NotNull(message = "Roles are required !")
        List<Integer> roleIds) {
}
