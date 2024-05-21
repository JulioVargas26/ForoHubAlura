package alura.challenge.model.users;

public record DatosListadoUsuario(Long id_usuario,
                                  String nombre,
                                  String email,
                                  String contrasena,
                                  Boolean activo) {

    public DatosListadoUsuario(Usuario usuario) {
        this(
                usuario.getId_usuario(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getContrasena(),
                usuario.getActivo()
        );
    }

}