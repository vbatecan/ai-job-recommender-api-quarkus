package com.vbatecan.job_recommender.service;

import com.vbatecan.job_recommender.exception.ExpiredTokenException;
import com.vbatecan.job_recommender.exception.InvalidTokenException;
import com.vbatecan.job_recommender.model.entity.ForgotPasswordCode;
import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.input.AuthenticationRequest;
import com.vbatecan.job_recommender.model.input.ForgotPasswordRequest;
import com.vbatecan.job_recommender.model.input.RegistrationRequest;
import com.vbatecan.job_recommender.model.output.LoginInformation;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Optional;

public interface AuthenticationService {

	/**
	 * Attempts to log in with the given request.
	 *
	 * @param request the login request
	 * @return the login information if the login is successful, empty otherwise
	 * @throws IllegalArgumentException if the login request is invalid
	 */
	Optional<LoginInformation> login(AuthenticationRequest request) throws IllegalArgumentException;

	/**
	 * Registers a new user using the given request.
	 *
	 * @param request the registration request
	 * @return the new user if the registration is successful, empty otherwise
	 * @throws IllegalArgumentException if the registration request is invalid
	 */
	Optional<User> register(RegistrationRequest request) throws IllegalArgumentException;

	boolean confirmForgotPassword(String code);

	Optional<ForgotPasswordCode> saveForgotPasswordRequest(ForgotPasswordRequest request);

	Optional<JsonWebToken> getToken(SecurityIdentity securityIdentity) throws ExpiredTokenException, InvalidTokenException;

	User getUserFromToken(JsonWebToken token) throws IllegalArgumentException, InvalidTokenException;
}
