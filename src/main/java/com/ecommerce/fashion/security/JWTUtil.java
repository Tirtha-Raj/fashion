package com.ecommerce.fashion.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.fashion.entity.RoleModel;
import com.ecommerce.fashion.repo.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	public static final long JWT_TOKEN_VALIDITY = 500 * 60 * 60;

	private final String secretKey = "A1GLJNHIKSR5454FSEKEJNKS54548RSKLODK564FLE44DKJF41";

	String secret_base64 = Base64.getEncoder().encodeToString(secretKey.getBytes());

	@Autowired
	private UserRepository userRepository;

	@Deprecated
	public String generateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.claim("authority", populate(userRepository.findByEmail(subject).get().getRole()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, secret_base64).compact();
	}

	private Object populate(RoleModel roles) {
		Set<String> authorities = new HashSet<>();
		authorities.add(roles.getRolename());
		return String.join(",", authorities);
	}

}
