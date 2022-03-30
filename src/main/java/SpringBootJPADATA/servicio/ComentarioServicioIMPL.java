package SpringBootJPADATA.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import SpringBootJPADATA.dto.ComentarioDTO;
import SpringBootJPADATA.entidad.Comentario;
import SpringBootJPADATA.entidad.Publicacion;
import SpringBootJPADATA.exceptions.BlogAppException;
import SpringBootJPADATA.exceptions.ResourceNotFoundException;
import SpringBootJPADATA.repositorio.ComentarioRepositorio;
import SpringBootJPADATA.repositorio.PublicacionRepositorio;

@Service
public class ComentarioServicioIMPL implements ComentarioServicio {
	@Autowired
	private ComentarioRepositorio comentarioRepositorio;
	@Autowired
	private PublicacionRepositorio publicacionRepositorio;
	@Autowired
	private ModelMapper modelMapper;

	public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
		Comentario comentario = mapearEntidad(comentarioDTO);
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		comentario.setPublicacion(publicacion);
		Comentario nuevoComentario = comentarioRepositorio.save(comentario);
		return mapearDTO(nuevoComentario);
	}

	// método para maper un comentario a DTO
	private ComentarioDTO mapearDTO(Comentario comentario) {
		ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
		return comentarioDTO;
	}

	// método para mapear un DTO a entidad
	private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {
		Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
		return comentario;

	}

	@Override
	public List<ComentarioDTO> obtenerComentarioPorPublicacionId(long publicacionId) {
		List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
		// devolvemos una lista con los comentarios de una publicacion usando
		// expresiones lambda
		return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
	}

	@Override
	public ComentarioDTO obtenerComentarioPorId(long publicacionID, long comentarioId) {
		Publicacion publicacion = publicacionRepositorio.findById(publicacionID)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionID));
		Comentario comentario = comentarioRepositorio.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
		if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentartio no pertenece a la publicacion");
		}
		return mapearDTO(comentario);
	}

	@Override
	public ComentarioDTO actualizarComentario(long publicacionId, Long comentarioId,
			ComentarioDTO solicitudComentario) {
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		Comentario comentario = comentarioRepositorio.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
		if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentartio no pertenece a la publicacion");
		}
		comentario.setId(solicitudComentario.getId());
		comentario.setNombre(solicitudComentario.getNombre());
		comentario.setEmail(solicitudComentario.getEmail());
		comentario.setMensaje(solicitudComentario.getCuerpo());
		Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
		return mapearDTO(comentarioActualizado);
	}

	@Override
	public void eliminarComentario(long publicacionId, Long idComentario) {
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
		Comentario comentario = comentarioRepositorio.findById(idComentario)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", idComentario));
		if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentartio no pertenece a la publicacion");
		}

		comentarioRepositorio.delete(comentario);

	}

}
