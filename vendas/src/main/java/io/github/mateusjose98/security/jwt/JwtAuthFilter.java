package io.github.mateusjose98.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.mateusjose98.service.impl.UsuarioServiceImpl;

public class JwtAuthFilter extends OncePerRequestFilter{
	
	private JwtService service;
	private UsuarioServiceImpl usuarioService;
	
	

	public JwtAuthFilter(JwtService service, UsuarioServiceImpl usuarioService) {
		
		this.service = service;
		this.usuarioService = usuarioService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizarion = request.getHeader("Authorization");
		if (authorizarion != null && authorizarion.startsWith("Bearer")) {
			String token = authorizarion.split(" ")[1];
			boolean valido = service.tokenValido(token);
			
			if(valido) {
				String loginUsuario = service.obterLoginUsuario(token);
				UserDetails usuario =  usuarioService.loadUserByUsername(loginUsuario);
				UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(user);
			}
		}
		filterChain.doFilter(request, response);
		
		
	}
	

}
