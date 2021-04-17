package io.github.mateusjose98.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		
	}
	
	@Override //autenticação
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
			.withUser("fulano")
			.password(passwordEncoder().encode("123"))
			.roles("USER", "ADMIN");
	}
	
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
			.and()
			.httpBasic();
		
		
	}

}
