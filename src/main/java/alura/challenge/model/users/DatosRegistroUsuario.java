package alura.challenge.model.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.UniqueElements;

public record DatosRegistroUsuario(

        @NotBlank
        String nombre,
        @NotNull
        @Email
        String email,
        @NotBlank
        String login,
        @NotBlank
        String contrasena
)  {
}
