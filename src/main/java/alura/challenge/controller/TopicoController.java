package alura.challenge.controller;

import alura.challenge.model.curso.*;
import alura.challenge.model.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo Topico en la base de datos")
    public ResponseEntity<DatosRespuestaTopico> RegistrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {

        System.out.println("Registrar Topico");

            Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId_topico(), topico.getTitulo(), topico.getMensaje(),
               topico.getCurso().getId_curso());


        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId_topico()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaTopico);

    }

    @GetMapping
    @Operation(summary = "Obtiene el listado de Topicos")
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        System.out.println("Listar Topicos");
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new));

    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los registros del medico con ID")
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicoporID(@PageableDefault(size = 10) Pageable paginacion,@PathVariable Long id){
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

        return ResponseEntity.ok(listadoTopicos);
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
    @Operation(summary = "Actualiza los datos de un medico existente")
    public ResponseEntity<Map<String, Object>> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
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

        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un medico registrado - inactivo")
    public ResponseEntity<Map<String, Object>> desactivarTopico(@PathVariable Long id){
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
        return ResponseEntity.ok(map);
    }

}
