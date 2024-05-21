package alura.challenge.model.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByStatusTrue(Pageable pageable);

    @Query("SELECT e FROM Topico e JOIN e.curso r ON r.nombre = :nombre ORDER BY RAND() LIMIT 1")
    Page<Topico> listarTopicoPorCurso(String nombre,Pageable pageable);

    @Query("SELECT e FROM Topico e WHERE e.id_topico = :id ORDER BY RAND() LIMIT 1")
    Page<Topico> listarTopicoPorId_Topico(Long id, Pageable pageable);
}
