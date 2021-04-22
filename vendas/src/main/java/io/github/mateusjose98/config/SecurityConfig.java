package io.github.mateusjose98.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.mateusjose98.security.jwt.JwtAuthFilter;
import io.github.mateusjose98.security.jwt.JwtService;
import io.github.mateusjose98.service.impl.UsuarioServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private JwtService jwtService;

	@Bean
	public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioService);
	}
	
//	@Override //autenticação
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		//método em memória, apeans para testes
////		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
////			.withUser("fulano")
////			.password(passwordEncoder().encode("123"))
////			.roles("USER", "ADMIN");
//		
//		//carregando da base de dados
//		auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
//		
//	}
	
	@Override //autorização (dos autenticados, quem tem acesso a o que)
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/clientes/**")
			.hasRole("USER")
			.antMatchers("/api/produtos/**")
			.hasRole("ADMIN")
			.antMatchers("/api/pedidos/**")
			.hasRole("USER")
			.antMatchers(HttpMethod.POST, "api/usuarios/**")
			.permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
		//			.httpBasic();
		
		
	}

}
