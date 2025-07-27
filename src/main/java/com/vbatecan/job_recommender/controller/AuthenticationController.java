package com.vbatecan.job_recommender.controller;

import com.vbatecan.job_recommender.exception.ExpiredTokenException;
import com.vbatecan.job_recommender.exception.InvalidTokenException;
import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.enumeration.UserRole;
import com.vbatecan.job_recommender.model.input.AuthenticationRequest;
import com.vbatecan.job_recommender.model.input.RegistrationRequest;
import com.vbatecan.job_recommender.model.output.AuthCheckResponse;
import com.vbatecan.job_recommender.model.output.LoginInformation;
import com.vbatecan.job_recommender.model.output.MessageResponse;
import com.vbatecan.job_recommender.service.AuthenticationService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@ApplicationScoped
@Path("/api/v1/auth")
public class AuthenticationController {

	@Inject
	AuthenticationService authenticationService;

	@Inject
	Logger log;

	@Inject
	SecurityIdentity securityIdentity;

	@POST
	@Path("/login")
	@Consumes(value = "application/json")
	public Response login(@Valid AuthenticationRequest request) throws IllegalArgumentException {
		if ( request == null ) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponse("Email and password should not be empty", false)).build();
		}

		Optional<LoginInformation> loginInformationOptional = authenticationService.login(request);
		if ( loginInformationOptional.isEmpty() ) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(new MessageResponse("Invalid email or password", false)).build();
		}

		LoginInformation loginInformation = loginInformationOptional.get();

		NewCookie cookie = new NewCookie.Builder("token")
			.httpOnly(true)
			.domain("http://localhost:8080")
			.secure(true)
		  	.value(loginInformation.token())
			.maxAge(3600)
			.build();

		return Response.ok(loginInformation).cookie(cookie).build();
	}

	@POST
	@Path("/register")
	public Response register(@Valid RegistrationRequest request) throws IllegalArgumentException {
		Optional<User> userOptional = authenticationService.register(request);

		if ( userOptional.isEmpty() ) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(new MessageResponse("Email already exists", false)).build();
		}

		return Response.ok(userOptional.get()).build();
	}

	@Path("/is_authenticated")
	@GET
	public Response isAuthenticated() {
		try {
			Optional<JsonWebToken> jwtTokenOptional = authenticationService.getToken(securityIdentity);
			if ( jwtTokenOptional.isEmpty() ) {
				return Response.status(Response.Status.UNAUTHORIZED)
					.entity(new AuthCheckResponse(false, 0L, UserRole.GUEST))
					.build();
			}

			JsonWebToken jwtToken = jwtTokenOptional.get();
			UserRole userRole = UserRole.GUEST; // Default
			if ( securityIdentity.hasRole("EMPLOYER") ) userRole = UserRole.EMPLOYER;
			if ( securityIdentity.hasRole("CANDIDATE") ) userRole = UserRole.CANDIDATE;
			return Response.ok()
				.entity(new AuthCheckResponse(true, jwtToken.getExpirationTime(), userRole))
				.build();
		} catch ( ExpiredTokenException | InvalidTokenException e ) {
			return Response.status(Response.Status.UNAUTHORIZED)
				.entity(new AuthCheckResponse(false, 0L, UserRole.GUEST))
				.build();
		} catch ( Exception e ) {
			log.error("Error while checking authentication", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(new AuthCheckResponse(false, 0L, UserRole.GUEST))
				.build();
		}
	}

	@GET
	@Path("/logout")
	public Response logout() {
		NewCookie cookie = new NewCookie.Builder("token")
			.value("")
			.maxAge(0)
			.httpOnly(true)
			.secure(true)
			.expiry(Date.from(Instant.now()))
			.build();
		return Response.ok().cookie(cookie).build();
	}
}
