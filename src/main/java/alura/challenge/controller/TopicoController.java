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
    public Map<String, Object> RegistrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("Registrar Topico");
        try {
            Curso curso = cursoRepository.getReferenceById(datosRegistroTopico.curso());
            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, curso));

            if (topico == null) {
                map.put("mensaje", "Error en el registro");
            } else {
                map.put("mensaje", "Topico registrado con ID=>" + topico.getId_topico());
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
    public Page<DatosListadoTopico> listadoTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        System.out.println("Listar Topicos");
        return topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new);

    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Page<DatosListadoTopico> listadoTopicoporID(@PageableDefault(size = 10) Pageable paginacion,@PathVariable Long id){
        System.out.println("Listar Topico por ID");
        Page<DatosListadoTopico> listadoTopicos = null;
        try {
            if(id.longValue() == 0) {
                listadoTopicos = topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new);
            }else {
                listadoTopicos = topicoRepository.listarTopicoPorId_Topico(id,paginacion).map(DatosListadoTopico::new);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listadoTopicos;
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
    public Map<String, Object> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Map<String, Object> map = new HashMap<>();
        try {
            Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id_topico()).actualizarDatos(datosActualizarTopico);

            if(topico == null) {
                map.put("mensaje", "Error en la actualización");
            }else {
                map.put("mensaje", "Se actualizó el Libro con ID=>" + topico.getId_topico());
                System.out.println(datosActualizarTopico);
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
        System.out.println("Eliminar Topico");
        Map<String, Object> map = new HashMap<>();

        try {
            Topico objSalida = topicoRepository.getReferenceById(id);
            if(objSalida.isStatus() == false) {
                topicoRepository.deleteById(id);
                map.put("mensaje","El Topico "+ objSalida.getId_topico() +" ha sido eliminado totalmente");
            }else {
                topicoRepository.getReferenceById(id).desactivarTopico();
                map.put("mensaje","El Topico "+ objSalida.getId_topico() +" ha sido desactivado logicamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("mensaje", "Error en la eliminación");
        }
        return map;
    }

}
