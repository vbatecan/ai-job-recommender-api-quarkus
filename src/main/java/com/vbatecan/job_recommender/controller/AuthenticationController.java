package com.vbatecan.job_recommender.controller;

import com.vbatecan.job_recommender.exception.ExpiredTokenException;
import com.vbatecan.job_recommender.exception.InvalidTokenException;
import com.vbatecan.job_recommender.mapping.UserMapper;
import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.enumeration.UserRole;
import com.vbatecan.job_recommender.model.input.AuthenticationRequest;
import com.vbatecan.job_recommender.model.input.ForgotPasswordRequest;
import com.vbatecan.job_recommender.model.input.MailRequest;
import com.vbatecan.job_recommender.model.input.RegistrationRequest;
import com.vbatecan.job_recommender.model.output.AuthCheckResponse;
import com.vbatecan.job_recommender.model.output.LoginInformation;
import com.vbatecan.job_recommender.model.output.MessageResponse;
import com.vbatecan.job_recommender.service.AuthenticationService;
import com.vbatecan.job_recommender.service.MailerService;
import com.vbatecan.job_recommender.service.TemplateService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@ApplicationScoped
@Path("/api/v1/auth")
public class AuthenticationController {

	@Inject UserMapper userMapper;
	@Inject AuthenticationService authenticationService;
	@Inject MailerService mailerService;
	@Inject TemplateService templateService;
	@Inject Logger log;
	@Inject SecurityIdentity securityIdentity;

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
			.path("/")
			.secure(false)
			.version(1)
			.value(loginInformation.token())
			.maxAge(
				60 * 60 * 24 * 7
			)
			.expiry(
				Date.from(
					Instant.now().plus(1, ChronoUnit.DAYS)
				)
			)
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
	@Authenticated
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
	@Path("/me")
	@Authenticated
	public Response me() {
		try {
			JsonWebToken token = ( JsonWebToken ) securityIdentity.getPrincipal();
			User user = authenticationService.getUserFromToken(token);

			return Response.ok()
				.entity(userMapper.toDto(user))
				.build();
		} catch ( ClassCastException e ) {
			log.error("Error while checking authentication", e);
			return Response.status(Response.Status.UNAUTHORIZED)
				.entity(new AuthCheckResponse(false, 0L, UserRole.GUEST))
				.build();
		}
	}

	@GET
	@Path("/logout")
	@PermitAll
	public Response logout() {
		NewCookie newCookie = new NewCookie.Builder("token")
			.maxAge(0)
			.build();

		return Response.ok().cookie(newCookie).build();
	}

	@POST
	@Path("/forgot-password/request")
	@PermitAll
	public Response forgotPasswordRequest(@Valid ForgotPasswordRequest request) {
		SecureRandom random = new SecureRandom();
		// Generate 6 alphanumeric code
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder code = new StringBuilder();
		for ( int i = 0; i < 6; i++ ) {
			int randomIndex = random.nextInt(chars.length());
			code.append(chars.charAt(randomIndex));
		}
		String randomCode = code.toString();
		String forgotPasswordTemplate = templateService.loadTemplate("forgot-password.html");
		forgotPasswordTemplate = forgotPasswordTemplate.replace("%CODE%", randomCode);

		MailRequest mailRequest = new MailRequest(
			request.email(),
			"Forgot Password Request",
			forgotPasswordTemplate
		);

		mailerService.sendEmail(mailRequest);
		return Response.ok(new MessageResponse(
			"If the email is correct, the code will be sent. Please check the spam and refresh your inbox. If it still doesn't work, please try again later.",
			true
		)).build();
	}

	@POST
	@Path("/forgot-password/{code}")
	@PermitAll
	public Response forgotPasswordSubmit(@PathParam("code") @Valid @Size(min = 6, max = 6, message = "Code should be 6 characters.") String code) {
		return Response.ok().build();
	}

	@GET
	@Path("/mail-test")
	@PermitAll
	public Response mailTest() {
		mailerService.sendEmail(new MailRequest(
			"vbatecan@gmail.com",
			"Test",
			"Test"
		));

		return Response.ok().build();
	}
}
