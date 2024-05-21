package alura.challenge.model.topico;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosActualizarTopico(
        @NotNull Long id,
        String titulo,
        String mensaje) {
}
