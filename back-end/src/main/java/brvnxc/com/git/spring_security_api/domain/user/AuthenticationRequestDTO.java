package brvnxc.com.git.spring_security_api.domain.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Username is required")
        String password
) {}
