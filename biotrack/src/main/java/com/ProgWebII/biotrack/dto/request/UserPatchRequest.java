package com.ProgWebII.biotrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO para atualização parcial de usuário.
 * Todos os campos são opcionais - apenas os campos fornecidos serão atualizados.
 */
public record UserPatchRequest(
    String name,
    
    @Past(message = "A data de nascimento deve estar no passado")
    LocalDate birthDate,
    
    @Size(min = 8, max = 9, message = "O CEP deve ter 8 ou 9 caracteres (com ou sem hífen)")
    String zipCode,
    
    @Email(message = "Formato de e-mail inválido")
    String email,
    
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{6,}$",
        message = "A senha deve conter pelo menos uma letra e um número"
    )
    String password
) {
    // Record para atualização parcial - todos os campos são opcionais
}
