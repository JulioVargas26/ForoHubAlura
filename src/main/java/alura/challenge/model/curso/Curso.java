package alura.challenge.model.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "curso")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_curso;
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Curso(DatosRegistroCurso datosRegistroCurso) {
        this.nombre = datosRegistroCurso.nombre();
        this.categoria = datosRegistroCurso.categoria();
    }
    public Curso actualizarDatosCurso(DatosActualizarCurso datosActualizarCurso) {
        if (datosActualizarCurso.nombre() != null) {
            this.nombre = datosActualizarCurso.nombre();
        }
        if (datosActualizarCurso.categoria() != null) {
            this.categoria = datosActualizarCurso.categoria();
        }

        return this;
    }

}
