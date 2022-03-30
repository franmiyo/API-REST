package SpringBootJPADATA.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import SpringBootJPADATA.entidad.Comentario;

public class PublicacionDTO {
	//con las anotacicones @NotEmpty (no puede estar vacío) y @Size (tamaño determinado) hacemos que esos 
	//campos o atributos se comporten de una manera específica. estas anotaciones pertenecen al paquete
	//de validation cargado en el documento pom.xml a través de Maven
	
	private Long id;
	@NotEmpty
	@Size(min=2, message = "El titulo de la publicación debe tener mínimo 2 caracteres")
	private String titulo;
	@NotEmpty
	private String descripcion;
	@NotEmpty
	@Size(min=2, message = "El contenido de la publicación debe tener mínimo 2 caracteres")
	private String contenido;
	
	private Set<Comentario> comentarios;

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public PublicacionDTO(Long id, String titulo, String descripcion, String contenido, Set<Comentario> comentarios) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
		this.comentarios = comentarios;
	}

	public PublicacionDTO() {
		super();
	}

}
