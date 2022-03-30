package SpringBootJPADATA.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBootJPADATA.entidad.Publicacion;

public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long>{

}
