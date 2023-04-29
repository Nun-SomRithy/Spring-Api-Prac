package co.istad.Banking.api.accounttype.web;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateAccountTypeDto(
        @NotBlank(message = "Name is Required !") String name) {
}
