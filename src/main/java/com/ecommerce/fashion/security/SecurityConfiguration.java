package com.ecommerce.fashion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	@Order(1)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
		http.authorizeHttpRequests(request -> request.requestMatchers("/login/**", "/h2-console/**").permitAll()
				.requestMatchers("/bidding/add").hasAuthority("BIDDER").requestMatchers("/bidding/update/**")
				.hasAuthority("APPROVER").requestMatchers("/bidding/delete/**").hasAnyAuthority("BIDDER", "APPROVER")
				.requestMatchers("/bidding/list/**").hasAnyAuthority("BIDDER", "APPROVER")
				.requestMatchers("/bidding/listAll/**").hasAnyAuthority("BIDDER", "APPROVER"))
				.addFilterBefore(new AuthenticatonFilter(), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(
						sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	public SecurityConfiguration() {
		super();
	}
	
	
}
