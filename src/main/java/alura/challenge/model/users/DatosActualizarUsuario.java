package alura.challenge.model.users;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
        @NotNull Long id_usuario,
        String nombre,
        String email,
        String login,
        String contrasena
        ) {

}
