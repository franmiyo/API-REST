package SpringBootJPADATA.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import SpringBootJPADATA.dto.PublicacionDTO;
import SpringBootJPADATA.dto.PublicacionRespuesta;
import SpringBootJPADATA.entidad.Publicacion;
import SpringBootJPADATA.exceptions.ResourceNotFoundException;
import SpringBootJPADATA.repositorio.PublicacionRepositorio;

//Es una clase de servicio, se creará en el contexto de SpringBoot
@Service
public class PublicacionServicioIMPL implements PublicacionServicio {

	// Este objeto es el encargado de realizar la persistencia
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
		// Este método recibe un JSON que tenemos que mapear como objeto para poder
		// persistirlo en BBDD
		// Creamos una publicacion y llamamos al método que mapea esta operación
		Publicacion publicacion = mapearEntidad(publicacionDTO);
		// lo persistimos en BBD
		Publicacion nuevapublicacion = publicacionRepositorio.save(publicacion);
		// Creamos la respuesta de nuestro servidor y lo mapeamos a DTO con formato JSON
		PublicacionDTO publicacionResponse = mapearDTO(nuevapublicacion);
		// devolvemos la publicacion de respuesta
		return publicacionResponse;
	}

	@Override
	public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePaginas, int medidaPaginas, String ordenarPor,String sortDir) {
		//Creamos un objeto Sort (ignorando mayusculas), para ello usamos sortDir como nombre
		//indicativo del orden que llevarán los objetos que por defecto es "asc" (ascendente),
		//dicho objeto Sort ordenará los objetos de forma ascendente según el valor de ordenarPor de forma ascendente,
		//en caso contrario (para indicarle el caso contrario colocamos dos puntos ":") ordenará esos objetos de forma descendente
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
		
		Pageable pageable = PageRequest.of(numeroDePaginas, medidaPaginas,sort);
		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);
		// Creamos una lista de publiaciones y con el objeto inyectados buscamos todas
		// las publicaciones
		// y se las asignamos a la lista
		List<Publicacion> listaDePublicaciones = publicaciones.getContent();
		// la lista la añadimos a un flujo stream, le indicamos que cada elemento de la
		// lista (que es una publicacion)
		// lo mapee a publicacionDTO y por ultimo que lo muestre en una lista JSON
		// (Collectors)
		List<PublicacionDTO> contenido = listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(numeroDePaginas);
		publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltima(publicaciones.isLast());
		
		return publicacionRespuesta;

	}

	// este metodo convierte una Entidad a DTO
	private PublicacionDTO mapearDTO(Publicacion publicacion) {
		PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class); 
		return publicacionDTO;
	}

	// este metodo convierte un DTO a una Entidad
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
		return publicacion;
	}

	@Override
	public PublicacionDTO obtenerPublicacionporId(long id) {
		// creamos una publicacion y usando el objeto SpringBoot usamos su método
		// ".finById()" y le pasamos el id,
		// si no lo encuentra lanzamos una excepción ResourceNotFoundExcepcion (nombre
		// de la clase que hemos creado del
		// paquete de excepciones) pasandole los 3 parámetros que indicamos en esa misma
		// clase
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
		// devolvemos el objeto encontrado en formato publicacionDTO(JSON)
		return mapearDTO(publicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
		// creamos una publicacion y usando el objeto SpringBoot usamos su método
		// ".finById()" y le pasamos el id,
		// si no lo encuentra lanzamos una excepción ResourceNotFoundExcepcion (nombre
		// de la clase que hemos creado del
		// paquete de excepciones) pasandole los 3 parámetros que indicamos en esa misma
		// clase
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
		// Si lo encuentra lo mapeamos con el objeto publicacionDTO recibido y le
		// asignamos sus parámetros
		publicacion.setId(publicacionDTO.getId());
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());
		// lo persistimos en BBDD en una publicacion con los parámetros actualizados
		Publicacion publicacionactualizada = publicacionRepositorio.save(publicacion);
		// devolvemos (mapeando a publiacionDTO(formato JSON)) el objeto actualizado
		return mapearDTO(publicacionactualizada);
	}

	@Override
	public void eliminarPublicacion(long id) {
		// creamos una publicacion y usando el objeto SpringBoot usamos su método
		// ".finById()" y le pasamos el id,
		// si no lo encuentra lanzamos una excepción ResourceNotFoundExcepcion (nombre
		// de la clase que hemos creado del
		// paquete de excepciones) pasandole los 3 parámetros que indicamos en esa misma
		// clase
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
		// si lo encuentra, usando el objeto SpringBoot llamamos el método JPA
		// ".delete()" y lo eliminamos de la BBDD
		// pasandole la publicacion encontrada como parámetro
		publicacionRepositorio.delete(publicacion);

	}

}
