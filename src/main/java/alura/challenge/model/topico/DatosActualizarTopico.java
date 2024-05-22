package alura.challenge.model.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull Long id_topico,
        String titulo,
        String mensaje) {
}
