package alura.challenge.controller;

import alura.challenge.model.curso.*;
import alura.challenge.model.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    @ResponseBody
    public Map<String, Object> RegistrarBooks(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("Registrar Topico");
        try {
            Curso curso = cursoRepository.getReferenceById(datosRegistroTopico.curso());
            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, curso));

            if (topico == null) {
                map.put("mensaje", "Error en el registro");
            } else {
                map.put("mensaje", "Libro registrado con ID=>" + topico.getId());
                System.out.println(topico);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);
        return map;
    }

    @GetMapping
    @ResponseBody
    public Page<DatosListadoTopico> listadoTopico(@PageableDefault(size = 10) Pageable paginacion) {
        System.out.println("Listar Libros");
        return topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new);

    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Page<DatosListadoTopico> listadoTopicoporID(@PageableDefault(size = 10) Pageable paginacion,@PathVariable Long id){
        System.out.println("Listar Libros por ID");
        Page<DatosListadoTopico> books = null;
        try {
            if(id.longValue() == 0) {
                books = topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new);
            }else {
                books = topicoRepository.listarTopicoPorId_Topico(id,paginacion).map(DatosListadoTopico::new);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    /*
    @GetMapping("/curso/{curso}")
    @ResponseBody
    public Page<DatosListadoTopico> listadoTopicoporNombre(@PageableDefault(size = 10) Pageable paginacion,@PathVariable String nombre){
        System.out.println("Listar Libros por curso");
        Page<DatosListadoTopico> books = null;
        try {
            if(nombre == null) {
                books = topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new);
                System.out.println("Listar Libros" + books);
            }else {

                books = topicoRepository.listarTopicoPorCurso(nombre,paginacion).map(DatosListadoTopico::new);
                System.out.println("Listar Libros por curso " + books);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
*/

    @PutMapping
    @Transactional
    @ResponseBody
    public Map<String, Object> actualizarTopico(@RequestBody @Valid DatosActualizarTopico dtoBooksUpdate){
        Map<String, Object> map = new HashMap<>();
        try {
            Topico topico = topicoRepository.getReferenceById(dtoBooksUpdate.id()).actualizarDatos(dtoBooksUpdate);

            if(topico == null) {
                map.put("mensaje", "Error en la actualización");
            }else {
                map.put("mensaje", "Se actualizó el Libro con ID=>" + topico.getId());
                System.out.println(dtoBooksUpdate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map ;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Transactional
    public Map<String, Object> desactivarTopico(@PathVariable Long id){
        System.out.println("Eliminar Libros");
        Map<String, Object> salida = new HashMap<>();

        try {
            Topico objSalida = topicoRepository.getReferenceById(id);
            if(objSalida.isStatus() == false) {
                topicoRepository.deleteById(id);
                salida.put("mensaje","El libro "+ objSalida.getId() +" ha sido eliminado totalmente");
            }else {
                topicoRepository.getReferenceById(id).desactivarTopico();
                salida.put("mensaje","El libro "+ objSalida.getId() +" ha sido desactivado logicamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            salida.put("mensaje", "Error en la eliminación");
        }
        return salida;
    }

}
