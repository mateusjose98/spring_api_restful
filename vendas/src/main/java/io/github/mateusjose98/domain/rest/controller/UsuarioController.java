package io.github.mateusjose98.domain.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.mateusjose98.domain.entity.Usuario;
import io.github.mateusjose98.exception.SenhaInvalidaException;
import io.github.mateusjose98.rest.dto.CredenciaisDTO;
import io.github.mateusjose98.rest.dto.TokenDTO;
import io.github.mateusjose98.security.jwt.JwtService;
import io.github.mateusjose98.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioServiceImpl usuarioService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	@PostMapping("auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
		try {
			Usuario usuario = Usuario.builder().login(credenciais.getLogin())
					.senha(credenciais.getSenha())
					.build();
			UserDetails usuarioAutenticado = usuarioService.autenticar(
						usuario
					);
			String token = jwtService.gerarToken(usuario);
			return new TokenDTO(usuario.getLogin(), token);
		}catch (UsernameNotFoundException e ) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}catch (SenhaInvalidaException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
			
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario salvar(@RequestBody Usuario usuario) {
		String passCript = passwordEncoder.encode(usuario.getSenha());
		usuario.setSenha(passCript);
		return usuarioService.salvar(usuario);
		
	}
	
	
}
