package com.vbatecan.job_recommender.impl;

import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.input.AuthenticationRequest;
import com.vbatecan.job_recommender.model.input.RegistrationRequest;
import com.vbatecan.job_recommender.service.AuthenticationService;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {

	private final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	/**
	 * Authenticate a user with their email and password.
	 *
	 * @param request user email and password
	 * @return the corresponding user if authenticated successfully, otherwise throw
	 * IllegalArgumentException
	 */
	@Override
	public User login(AuthenticationRequest request) throws IllegalArgumentException {
		return null;
	}

	/**
	 * Register a new user with the provided registration details.
	 *
	 * @param request the registration request containing user details
	 * @return the registered user entity
	 * @throws IllegalArgumentException if the registration details are invalid
	 */
	@Override
	public User register(RegistrationRequest request) throws IllegalArgumentException {
		return null;
	}
}
