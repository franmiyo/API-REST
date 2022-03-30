package SpringBootJPADATA.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import SpringBootJPADATA.seguridad.CustomUserDetailsService;

@Configuration
//la anotacion @EnableWebSecurity nos permite crear una clase de seguridad personalizada
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//Al heredar de WebSecurityConfigurerAdapter nos permite redefinir algunos de los metodos propios de spring security
// y nos obliga a hacer hash con las contraseñas para que no se vean expuestas en texto plano 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Con este sentencia permitimos todas las peticiones GET, el resto de
		// peticiones
		// requieren una autenticacion basica
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll()
				.antMatchers("/api/auth/**").permitAll().anyRequest().authenticated().and().httpBasic();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
