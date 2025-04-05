package com.ecommerce.fashion.intf;

import org.springframework.http.ResponseEntity;

import com.ecommerce.fashion.entity.AuthenticationModel;
import com.ecommerce.fashion.entity.UserModel;

public interface IUserService {

	UserModel signUpUser(UserModel userRequest);

	ResponseEntity<Object> matchSecurityFlag(UserModel userRequest);

	ResponseEntity<Object> updatePassword(AuthenticationModel authenticationModel);

}
