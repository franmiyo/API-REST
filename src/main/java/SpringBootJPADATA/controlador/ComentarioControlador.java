package SpringBootJPADATA.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBootJPADATA.dto.ComentarioDTO;
import SpringBootJPADATA.servicio.ComentarioServicio;

@RestController
@RequestMapping("/api/")
public class ComentarioControlador {

	@Autowired
	private ComentarioServicio comentarioServicio;
	
	//En @PostMapping debemos agregar dentro de los argumentos que recibe la anotacion @Valid
	//para que vea las anotaciones de validacion que hay en ComentariosDTO
	@PostMapping("/publicaciones/{publicacionId}/comentarios")
	public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value = "publicacionId") long publicacionId,
			@Valid @RequestBody ComentarioDTO comentarioDTO) {
		return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO),
				HttpStatus.CREATED);
	}

	@GetMapping("/publicaciones/{publicacionId}/comentarios")
	public List<ComentarioDTO> listarComentariosPorPublicacionId(
			@PathVariable(value = "publicacionId") Long publicacionId) {
		return comentarioServicio.obtenerComentarioPorPublicacionId(publicacionId);

	}

	@GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
	public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(
			@PathVariable(value = "publicacionId") Long publicacionId, @PathVariable(value = "id") long comentarioId) {
		ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
		return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
	}

	@PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
	public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value = "publicacionId") Long publicacionId,
			@PathVariable(value = "id") long comentarioId, @RequestBody ComentarioDTO comentarioDTO) {
		ComentarioDTO comentarioActualizado = comentarioServicio.actualizarComentario(publicacionId, comentarioId,
				comentarioDTO);
		return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
	}

	@DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
	public ResponseEntity<String> eliminarComentario(@PathVariable(value = "publicacionId") Long publicacionId,
			@PathVariable(value = "id") long comentarioId) {
		comentarioServicio.eliminarComentario(publicacionId, comentarioId);
		return new ResponseEntity<>("Comentario emliminado", HttpStatus.OK);

	}

}
