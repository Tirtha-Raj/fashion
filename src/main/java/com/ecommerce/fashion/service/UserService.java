package com.ecommerce.fashion.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.fashion.entity.AuthenticationModel;
import com.ecommerce.fashion.entity.UserModel;
import com.ecommerce.fashion.intf.IUserService;
import com.ecommerce.fashion.repo.UserRepository;
import com.ecommerce.fashion.util.FashionConstant;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserModel signUpUser(UserModel userRequest) {
		UserModel userModel = userRepository.save(userRequest);
		return userModel;
	}

	@Override
	public ResponseEntity<Object> matchSecurityFlag(UserModel userRequest) {
		Optional<UserModel> user = userRepository.findByEmail(userRequest.getEmail());
		if (user.isPresent()) {
			String securityQuestion = user.get().getSecurityQuestion();
			if (StringUtils.equalsIgnoreCase(userRequest.getSecurityQuestion(), securityQuestion)) {
				user.get().setMatchSecurityFlag(true);
			}
			UserModel userModel = userRepository.save(user.get());
			return ResponseEntity.status(HttpStatus.OK).body(userModel.getMatchSecurityFlag());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FashionConstant.INVALID_EMAIL);
	}

	@Override
	public ResponseEntity<Object> updatePassword(AuthenticationModel authenticationModel) {
		Optional<UserModel> user = userRepository.findByEmail(authenticationModel.getEmail());
		if (user.isPresent()) {
			user.get().setPassword(authenticationModel.getPassword());
			userRepository.save(user.get());
			return ResponseEntity.status(HttpStatus.OK).body(FashionConstant.PASSWORD_UPDATED);
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FashionConstant.INVALID_EMAIL);
	}

}
