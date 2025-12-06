package com.ProgWebII.biotrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UserRequest(
    @NotBlank(message = "O nome é obrigatório")
    String name,

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    LocalDate birthDate, // Data de Nascimento

    @NotBlank(message = "O CEP é obrigatório")
    @Size(min = 8, max = 9, message = "O CEP deve ter 8 ou 9 caracteres (com ou sem hífen)")
    String zipCode, // CEP

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    String email,

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{6,}$",
        message = "A senha deve conter pelo menos uma letra e um número"
    )
    String password // A senha deve ser armazenada como um hash (criptografada)!
) {
  // O corpo do record pode ficar vazio, pois a validação é feita nos parâmetros.
}