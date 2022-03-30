package SpringBootJPADATA.servicio;

import java.util.List;

import SpringBootJPADATA.dto.ComentarioDTO;

public interface ComentarioServicio {
	
	public ComentarioDTO crearComentario(long publicacionId,ComentarioDTO comentarioDTO);
	//Cuando liste una publicacion tambien listara sus comentartios
	public List<ComentarioDTO> obtenerComentarioPorPublicacionId(long publicacionId);
	public ComentarioDTO obtenerComentarioPorId(long publicacionID,long comentarioId);
	public ComentarioDTO actualizarComentario(long publicacionId,Long comentarioId, ComentarioDTO solicitudComentario);
	public void eliminarComentario(long publicacionId, Long idComentario);

}
