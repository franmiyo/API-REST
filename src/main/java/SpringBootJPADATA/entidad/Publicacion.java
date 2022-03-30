package SpringBootJPADATA.entidad;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
//Nombre de la tabla= publicaciones y uniqueconstraint indica que 
//no pueden haber dos publicaciones con el mismo titulo
@Table(name = "publicaciones", uniqueConstraints = { @UniqueConstraint(columnNames = { "titulo" }) })
public class Publicacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "titulo", nullable = false)
	private String titulo;
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	@Column(name = "contenido", nullable = false)
	private String contenido;

	// con los parámetros cascade y orphanRemoval en sus valores actuales si
	// eliminamos
	// una publicacion se eliminaran todos sus objetos asociados
	//fetch=FetchType.EAGER -> Esto hace que los comentarios se instancien junto con el resto de los atributos.
	//con la anotacion @JsonBackReference ignora los datos a la hora de serializarlos para no entrar en bucles
	//infinitos
	@JsonBackReference
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	private Set<Comentario> comentarios = new HashSet<>();

	public Long getId() {
		return id;
	}

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
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

	// constructores

	

	public Publicacion(Long id, String titulo, String descripcion, String contenido, Set<Comentario> comentarios) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
		this.comentarios = comentarios;
	}

	public Publicacion() {
		super();
	}

}
