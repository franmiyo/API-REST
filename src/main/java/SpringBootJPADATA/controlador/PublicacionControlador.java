package SpringBootJPADATA.controlador;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import SpringBootJPADATA.dto.PublicacionDTO;
import SpringBootJPADATA.dto.PublicacionRespuesta;
import SpringBootJPADATA.servicio.PublicacionServicio;
import SpringBootJPADATA.utilidades.AppConstantes;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {
	// inyectamos la dependencia que se encargará de la persistencia
	@Autowired
	private PublicacionServicio publicacionServicio;

	@GetMapping
	public PublicacionRespuesta listarPublicaciones(
			@RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroPagina,
			@RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaPegina,
			@RequestParam(value ="sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false)String ordenarPor,
			@RequestParam(value ="sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false)String sortDir){
		// devuelve todas las publicaciones
		return publicacionServicio.obtenerTodasLasPublicaciones(numeroPagina,medidaPegina,ordenarPor,sortDir);
	}

	@GetMapping("/{id}")
	// Cuando creemos la sentencia HTTP le debemos pasar un id y buscará en BBDD por
	// ello
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") long id) {
		// devolvemos un ResponseEntity con código HTTP -> OK pasandole la publicacion
		// usando el objeto inyectado SpringBoot
		// que a su vez recibe como parámetro el id indicado
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionporId(id));
	}

	//En @PostMapping debemos agregar dentro de los argumentos que recibe la anotacion @Valid
	//para que vea las anotaciones de validacion que hay en PublicacionDTO
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO) {
		// devolvemos el objeto creado con el mismo formato que el recibido
		// (PublicacionDTO)
		return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	// Cuando creemos la sentencia HTTP le debemos pasar los datos que queremos
	// cambiar del objeto y el id
	// de ese objeto así actualizará en BBDD
	public ResponseEntity<PublicacionDTO> actualizarPublicacion(@RequestBody PublicacionDTO publicacionDTO,
			@PathVariable(name = "id") long id) {
		// en un publicacionDTO llamamos al método que actualiza el objeto por id
		PublicacionDTO publicacionRespuesta = publicacionServicio.actualizarPublicacion(publicacionDTO, id);
		// devolvemos un ResponseEntity con código HTTP (como segundo parámetro) -> OK
		// pasandole la publicacionRespuesta
		return new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	// Cuando creemos la sentencia HTTP le debemos pasar el id del objeto que
	// queremos eliminar
	// así eliminará en BBDD, deolverá un String con mensaje de éxito
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id) {
		// usando el objeto SpringBoot llamamos a nuestro método eliminarPubliacion(id)
		// pasandole el id
		publicacionServicio.eliminarPublicacion(id);
		// devolvemos un ResponseEntity con el mensaje de éxito y el código HTTP-> OK
		return new ResponseEntity<>("Publicacion eliminada con éxito", HttpStatus.OK);
	}

}
