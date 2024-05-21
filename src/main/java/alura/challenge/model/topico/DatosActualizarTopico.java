package alura.challenge.model.topico;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosActualizarTopico(
        @NotNull Long id_topico,
        String titulo,
        String mensaje) {
}
