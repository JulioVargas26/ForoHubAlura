package alura.challenge.model.curso;

public record DatosListadoCurso(
        Long id_curso,
        String nombre,
        String categoria) {

    public DatosListadoCurso(Curso curso) {
        this(curso.getId_curso(), curso.getNombre(), curso.getCategoria().toString());
    }
}
