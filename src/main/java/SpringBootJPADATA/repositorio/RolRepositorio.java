package SpringBootJPADATA.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBootJPADATA.entidad.Rol;


public interface RolRepositorio extends JpaRepository<Rol, Long>{
	
	public Optional<Rol> findByNombre(String nombre);

}
