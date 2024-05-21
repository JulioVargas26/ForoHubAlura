package alura.challenge.model.topico;


import java.time.LocalDate;

public record DatosListadoTopico(
        Long id_topico,
        String titulo,
        String mensaje,
        LocalDate fechaCreacion,
        Boolean status,
        String curso) {

    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId_topico(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.isStatus(),
                topico.getCurso().getNombre());
    }
}