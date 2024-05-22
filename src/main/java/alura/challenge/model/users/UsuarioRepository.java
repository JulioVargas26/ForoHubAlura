package alura.challenge.model.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String username);

    boolean existsByLogin(String login);

    @Query("select u from Usuario u where u.activo = true")
    Page<Usuario> listarRegistrosActivoTrue(Pageable paginacion);

    @Query("select u from Usuario u where u.email like :ape")
    Page<Usuario> listarPorApellidoLike(Pageable paginacion,String ape);
}
