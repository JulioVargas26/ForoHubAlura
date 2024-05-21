package alura.challenge.controller;

import alura.challenge.model.users.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    @ResponseBody
    public Map<String, Object> RegistrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario){
        Map<String, Object> salida = new HashMap<>();
        System.out.println("Registrar Usuario");
        try {
            Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));

            if(usuario == null) {
                salida.put("mensaje", "Error en el registro");
            }else {
                salida.put("mensaje", "Usuario registrado con ID=>" + usuario.getId_usuario());
                System.out.println(datosRegistroUsuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(salida);
        return salida;
    }

    @GetMapping
    @ResponseBody
    public Page<DatosListadoUsuario> listarUsuarios(@PageableDefault(size = 10) Pageable paginacion) {
        System.out.println("Listar Usuarios");
        return usuarioRepository.listarRegistrosActivoTrue(paginacion).map(DatosListadoUsuario::new);

    }

    @GetMapping("/{ape}")
    @ResponseBody
    public Page<DatosListadoUsuario> listarUsuariosporApellido(@PageableDefault(size = 10) Pageable paginacion,@PathVariable String ape){
        System.out.println("Listar Usuario por Correo");
        Page<DatosListadoUsuario> users = null;
        try {
            if(ape.isEmpty()) {
                users = usuarioRepository.listarRegistrosActivoTrue(paginacion).map(DatosListadoUsuario::new);
            }else {
                users = usuarioRepository.listarPorApellidoLike(paginacion,ape).map(DatosListadoUsuario::new);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    @PutMapping
    @Transactional
    @ResponseBody
    public Map<String, Object> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario){
        Map<String, Object> map = new HashMap<>();
        try {
            Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id_usuario()).actualizarDatos(datosActualizarUsuario);
            if(usuario == null) {
                map.put("mensaje", "Error en la actualización");
            }else {
                map.put("mensaje", "Se actualizó el Usuario con ID=>" + usuario.getId_usuario());
                System.out.println(datosActualizarUsuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map ;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Transactional
    public Map<String, Object> desactivarUsuario(@PathVariable Long id){
        System.out.println("Eliminar Usuario");
        Map<String, Object> salida = new HashMap<>();

        try {
            Usuario objSalida = usuarioRepository.getReferenceById(id);
            if(objSalida.getActivo() == false) {
                usuarioRepository.deleteById(id);
                salida.put("mensaje","El Usuario "+ objSalida.getId_usuario() +" ha sido eliminado totalmente");
            }else {
                usuarioRepository.getReferenceById(id).desactivarUsuario();
                salida.put("mensaje","El Usuario "+ objSalida.getId_usuario() +" ha sido desactivado logicamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", "Error en la eliminación");
        }
        return salida;
    }
}
