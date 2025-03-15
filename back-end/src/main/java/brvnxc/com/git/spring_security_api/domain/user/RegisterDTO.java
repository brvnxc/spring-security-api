package brvnxc.com.git.spring_security_api.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "Nao pode ser nulo")
        @Size(min = 3, max = 20, message = "Deve ter entre 3 e 20 caracteres")
        String username,
        @NotBlank(message = "Nao pode ser nulo")
        @Email(message = "Informe um email valido")
        String email,
        @NotBlank(message = "Nao pode ser nulo")
        @Size(min = 6, max = 30, message = "Deve ter mais que 6 caracteres")
        String password,
        UserRole role
) {}
