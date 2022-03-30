package SpringBootJPADATA.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBootJPADATA.entidad.Comentario;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {
	public List<Comentario> findByPublicacionId(long publicacionId);

}
