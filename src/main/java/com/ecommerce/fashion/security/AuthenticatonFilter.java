package com.ecommerce.fashion.security;

import java.io.IOException;
import java.util.Base64;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticatonFilter extends OncePerRequestFilter {
	
	private final String secretKey="A1GLJNHIKSR5454FSEKEJNKS54548RSKLODK564FLE44DKJF41";
	
	String secret_base64 = Base64.getEncoder().encodeToString(secretKey.getBytes());

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if(token != null && SecurityContextHolder.getContext().getAuthentication()== null) {
			token = token.substring(7);
			Claims claims = Jwts.parser().setSigningKey(secret_base64).build().parseClaimsJws(token).getBody();
			String username = claims.getSubject();
			String authorities = (String) claims.get("authority");
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null,AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
		filterChain.doFilter(request, response);
	}

}

