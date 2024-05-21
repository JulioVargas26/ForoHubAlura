package alura.challenge.model.users;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosActualizarUsuario(
        @NotNull Long id_usuario,
        String nombre,
        String email,
        String contrasena
        ) {

}
