package com.vbatecan.job_recommender.service;

import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.input.AuthenticationRequest;
import com.vbatecan.job_recommender.model.input.RegistrationRequest;

public interface AuthenticationService {

	/**
	 * Authenticate a user with their email and password.
	 *
	 * @param request user email and password
	 * @return the corresponding user if authenticated successfully, otherwise throw
	 * IllegalArgumentException
	 */
	User login(AuthenticationRequest request) throws IllegalArgumentException;

	/**
	 * Register a new user with the provided registration details.
	 *
	 * @param request the registration request containing user details
	 * @return the registered user entity
	 * @throws IllegalArgumentException if the registration details are invalid
	 */
	User register(RegistrationRequest request) throws IllegalArgumentException;
}
