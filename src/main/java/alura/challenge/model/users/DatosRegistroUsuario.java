package alura.challenge.model.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(

        @NotBlank
        String nombre,
        @NotNull
        @Email
        String email,
        @NotBlank
        @Valid
        String contrasena
)  {
}
