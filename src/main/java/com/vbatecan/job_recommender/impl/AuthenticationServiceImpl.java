package com.vbatecan.job_recommender.impl;

import com.vbatecan.job_recommender.exception.ExpiredTokenException;
import com.vbatecan.job_recommender.exception.InvalidTokenException;
import com.vbatecan.job_recommender.mapping.UserMapper;
import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.input.AuthenticationRequest;
import com.vbatecan.job_recommender.model.input.RegistrationRequest;
import com.vbatecan.job_recommender.model.output.LoginInformation;
import com.vbatecan.job_recommender.model.output.MessageResponse;
import com.vbatecan.job_recommender.service.AuthenticationService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {

	private final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	/**
	 * Attempts to log in with the given request.
	 *
	 * @param request the login request
	 * @return the login information if the login is successful, empty otherwise
	 * @throws IllegalArgumentException if the login request is invalid
	 */
	@Override
	@Transactional
	public Optional<LoginInformation> login(AuthenticationRequest request) throws IllegalArgumentException {
		Optional<User> userOptional = User.getByEmail(request.email());
		if ( userOptional.isEmpty() ) {
			log.info("User with email {} not found", request.email());
			return Optional.empty();
		}

		User user = userOptional.get();
		if ( !BcryptUtil.matches(request.password(), user.getPassword()) ) {
			log.info("Incorrect password for user with email {}", request.email());
			return Optional.empty();
		}

		User.update("lastLogin", Instant.now());
		user.setLastLogin(Instant.now());
		String token = Jwt.issuer("http://localhost:8080")
			.claim("role", user.getRole())
			.expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
			.subject(user.getEmail())
			.sign();

		return Optional.of(new LoginInformation(
			UserMapper.INSTANCE.toDto(user),
			token,
			user.getRole()
		));
	}

	@Override
	public Optional<User> register(@Valid RegistrationRequest request) throws IllegalArgumentException {
		if (request == null) {
			throw new IllegalArgumentException("Registration request cannot be null");
		}

		Optional<User> userOptional = User.getByEmail(request.email());
		if ( userOptional.isPresent() ) {
			log.info("User with email {} already exists", request.email());
			return Optional.empty();
		}

		User user = new User();
		UserMapper.INSTANCE.partialUpdate(request, user);
		user.setPassword(BcryptUtil.bcryptHash(request.password()));
		user.persist();
		return Optional.of(user);
	}

	@Override
	public Optional<JsonWebToken> getToken(SecurityIdentity securityIdentity) throws InvalidTokenException, ExpiredTokenException {
		try {
			JsonWebToken token = ( JsonWebToken ) securityIdentity.getPrincipal();
			if ( !isIssuerValid(token) ) {
				return Optional.empty();
			}

			if (isTokenExpired(token)) {
				throw new ExpiredTokenException("Token already expired and must be discarded and refreshed");
			}

			return Optional.of(token);
		} catch ( ClassCastException e ) {
			throw new InvalidTokenException("Token format is not valid.");
		}
	}

	private boolean isTokenExpired(JsonWebToken token) {
		return token.getExpirationTime() * 1000 >= System.currentTimeMillis();
	}

	private boolean isIssuerValid(JsonWebToken token) {
		return token.getIssuer().equals("http://localhost:8080");
	}
}
