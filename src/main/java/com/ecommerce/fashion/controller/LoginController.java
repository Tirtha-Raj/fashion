package com.ecommerce.fashion.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.fashion.entity.AuthenticationModel;
import com.ecommerce.fashion.entity.AuthenticationResponse;
import com.ecommerce.fashion.entity.UserModel;
import com.ecommerce.fashion.repo.UserRepository;
import com.ecommerce.fashion.security.JWTUtil;

@RestController
public class LoginController {

	@Autowired
	private AuthenticationProvider authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<Object> authenticateUser(@RequestBody AuthenticationModel authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}

		Optional<UserModel> user = userRepository.findByEmail(authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(user.get().getEmail());
		if (jwt != null) {
			AuthenticationResponse loginResponse = new AuthenticationResponse();
			loginResponse.setJwt(jwt);
			loginResponse.setStatus("200");
			loginResponse.setUsername(user.get().getUsername());
			return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}