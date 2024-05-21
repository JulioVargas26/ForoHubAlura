package alura.challenge.model.curso;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotNull Long id_curso,
        String nombre,
        Categoria categoria) {
}
