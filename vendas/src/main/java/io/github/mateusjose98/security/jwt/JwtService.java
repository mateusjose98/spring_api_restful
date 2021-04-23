package io.github.mateusjose98.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.github.mateusjose98.VendasApplication;
import io.github.mateusjose98.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service			
public class JwtService {
	
	
	@Value("${security.jwt.expiracao}")
	private String expiracao;
	
	@Value("${security.jwt.chave-assinatura}")
	private String chaveAssinatura;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
		JwtService service = contexto.getBean(JwtService.class);
		Usuario usuario = Usuario.builder().login("fulano").build();
		String token = service.gerarToken(usuario);
		System.out.println(token);
	}
	
	public String gerarToken(Usuario usuario) {
		long expString = Long.valueOf(expiracao);
		
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		Date data = Date
					.from(dataHoraExpiracao
							.atZone(ZoneId
									.systemDefault())
							.toInstant());
//		HashMap<String, Object> claims = new HashMap<>();
//		claims.put("roles", "admin");
		
		return Jwts.builder()
				.setSubject(usuario.getLogin())
				.setExpiration(data)
//				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
				.compact();
	
	}
	
	public boolean tokenValido(String token) {
		try {
			return new Date()
					.before(obterClaims(token)
							.getExpiration());
			
		}catch(Exception e){
			return false;
		}
	}
	
	public String obterLoginUsuario(String token)  throws ExpiredJwtException{
		return (String) obterClaims(token).getSubject();
	}
	
	public Claims obterClaims(String token) throws ExpiredJwtException{
		return Jwts.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJwt(token)
				.getBody();
	}

}
