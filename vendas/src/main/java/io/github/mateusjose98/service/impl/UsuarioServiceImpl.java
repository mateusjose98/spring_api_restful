package io.github.mateusjose98.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.mateusjose98.domain.entity.Usuario;
import io.github.mateusjose98.domain.repository.UsuarioRepository;
import io.github.mateusjose98.exception.SenhaInvalidaException;

@Service
public class UsuarioServiceImpl implements UserDetailsService{
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepository repository;
	

	public UserDetails autenticar(Usuario usuario) {
		UserDetails user = loadUserByUsername(usuario.getLogin());
		boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());
		if(senhasBatem) {
			return user;
		} 
		throw new SenhaInvalidaException();
	}
	
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return repository.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario user = repository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado"));
		
		
		String[] roles = user.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		return User.builder()
				.username(user.getLogin())
				.password(user.getSenha())
				.roles(roles)
				.build();
		
	}

}
