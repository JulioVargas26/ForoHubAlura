package alura.challenge.model.users;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

//import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
 //   UserDetails findByLogin(String username);

    @Query("select u from Usuario u where u.activo = true")
    Page<Usuario> listarRegistrosActivoTrue(Pageable paginacion);

    @Query("select u from Usuario u where u.email like :ape")
    Page<Usuario> listarPorApellidoLike(Pageable paginacion,String ape);
}
