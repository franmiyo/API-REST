package SpringBootJPADATA.controlador;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBootJPADATA.dto.LoginDTO;
import SpringBootJPADATA.dto.RegistroDTO;
import SpringBootJPADATA.entidad.Rol;
import SpringBootJPADATA.entidad.Usuario;
import SpringBootJPADATA.repositorio.RolRepositorio;
import SpringBootJPADATA.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {
	// Todo componente de Spring (@Bean por ejemplo) se puede inyectar en las clases
	// para su uso específico

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private RolRepositorio rolRepositorio;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/iniciarSesion")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("Ha iniciado sesion con éxito", HttpStatus.OK);
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO) {

		if (usuarioRepositorio.existsByUsername(registroDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
		}
		if (usuarioRepositorio.existsByEmail(registroDTO.getEmail())) {
			return new ResponseEntity<>("Ese email ya existe", HttpStatus.BAD_REQUEST);
		}
		Usuario usuario = new Usuario();
		usuario.setNombre(registroDTO.getNombre());
		usuario.setUsername(registroDTO.getUsername());
		usuario.setEmail(registroDTO.getEmail());
		usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
		Rol roles = rolRepositorio.findByNombre("ROLE_ADMIN").get();
		// singleton es una coleccionde 1 posicion
		usuario.setRoles(Collections.singleton(roles));
		usuarioRepositorio.save(usuario);
		return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.OK);
	}
}
