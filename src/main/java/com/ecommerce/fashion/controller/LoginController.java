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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.fashion.entity.AuthenticationModel;
import com.ecommerce.fashion.entity.AuthenticationResponse;
import com.ecommerce.fashion.entity.UserModel;
import com.ecommerce.fashion.intf.IUserService;
import com.ecommerce.fashion.repo.UserRepository;
import com.ecommerce.fashion.security.JWTUtil;

@RestController
@RequestMapping("/authentication")
public class LoginController {

	@Autowired
	private AuthenticationProvider authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IUserService iUserService;

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
		AuthenticationResponse response = generateJWT(user);
		if (response.getStatus().equals("200")) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/sign-up")
	public ResponseEntity<Object> signUpUser(@RequestBody UserModel userRequest) throws Exception {

		UserModel userModel = iUserService.signUpUser(userRequest);

		Optional<UserModel> user = Optional.of(userModel);
		AuthenticationResponse response = generateJWT(user);
		if (response.getStatus().equals("200")) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping("/security-question")
	public ResponseEntity<Object> matchSecurityFlag(@RequestBody UserModel userRequest) {		
		return iUserService.matchSecurityFlag(userRequest);
		
	}
	
	@PostMapping("/update-password")
	public ResponseEntity<Object> updatePassword(@RequestBody AuthenticationModel authenticationModel) {	
		return iUserService.updatePassword(authenticationModel);
		
	}

	private AuthenticationResponse generateJWT(Optional<UserModel> user) {
		final String jwt = jwtUtil.generateToken(user.get().getEmail());
		AuthenticationResponse loginResponse = new AuthenticationResponse();
		if (jwt != null) {
			loginResponse.setJwt(jwt);
			loginResponse.setStatus("200");
			loginResponse.setUsername(user.get().getUsername());
		} else {
			loginResponse.setStatus("400");
		}
		return loginResponse;
	}
}