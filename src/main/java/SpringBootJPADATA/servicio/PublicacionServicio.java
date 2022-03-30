package SpringBootJPADATA.servicio;



import SpringBootJPADATA.dto.PublicacionDTO;
import SpringBootJPADATA.dto.PublicacionRespuesta;

public interface PublicacionServicio {

	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
	public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePaginas, int medidaPaginas, String ordenarPor, String sortDir);
	public PublicacionDTO obtenerPublicacionporId(long id);
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO,long id);
	public void eliminarPublicacion(long id);
}
