package alura.challenge.model.users;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;


@Table(name = "usuarios")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    private String nombre;
    private String email;
    private String contrasena;
    private Boolean activo = true;

    public Usuario(DatosRegistroUsuario datosRegistroUsuario) {
        this.nombre = datosRegistroUsuario.nombre();
        this.email = datosRegistroUsuario.email();
        this.contrasena = datosRegistroUsuario.contrasena();
    }

    public Usuario actualizarDatos(DatosActualizarUsuario datosActualizarUsuario) {
        if (datosActualizarUsuario.nombre() != null) {
            this.nombre = datosActualizarUsuario.nombre();
        }
        if (datosActualizarUsuario.email() != null) {
            this.email = datosActualizarUsuario.email();
        }
        if (datosActualizarUsuario.contrasena() != null) {
            this.contrasena = datosActualizarUsuario.contrasena();
        }
        return this;
    }

    public void desactivarUsuario() {
        this.activo = false;
    }

}

 /*implements UserDetails {


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
*/