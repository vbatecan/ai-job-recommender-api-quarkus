package com.vbatecan.job_recommender.controller;

import com.vbatecan.job_recommender.mapping.UserMapper;
import com.vbatecan.job_recommender.model.entity.User;
import com.vbatecan.job_recommender.model.input.PageRequest;
import com.vbatecan.job_recommender.model.input.UserUpdateInput;
import com.vbatecan.job_recommender.model.output.MessageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Path("/api/v1/users")
public class UserController {

	@Path("")
	@GET
	public Response listAll(@BeanParam PageRequest page) {
		List<User> users = User.findAll().page(page.toPage()).list();

		return Response.ok().entity(
			users.stream().map(UserMapper.INSTANCE::toDto)
		).build();
	}

	@Path("/{email}")
	@GET
	public Response get(@PathParam("email") String email) {
		Optional<User> userOptional = User.find("email", email).firstResultOptional();

		if ( userOptional.isPresent() ) {
			return Response.ok(
				userOptional.map(UserMapper.INSTANCE::toDto)
			).build();
		}

		return Response.status(Response.Status.NOT_FOUND).entity(new MessageResponse(
			"Email not found.",
			false
		)).build();
	}

	@Path("/{email}")
	@DELETE
	@Transactional
	public Response delete(@PathParam("email") String email) {
		Optional<User> userOptional = User.find("email", email).firstResultOptional();

		if ( userOptional.isPresent() ) {
			User user = userOptional.get();
			user.delete();
			return Response.ok(UserMapper.INSTANCE.toDto(user)).build();
		}

		return Response.status(Response.Status.NOT_FOUND).entity(new MessageResponse(
			"User not found.",
			false
		)).build();
	}

	@Path("/{email}")
	@PUT
	@Transactional
	public Response update(@PathParam("email") String email, @Valid UserUpdateInput updateInput) {
		if ( email == null ) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponse(
				"Email cannot be null",
				false
			)).build();
		}

		Optional<User> userOptional = User.find("email", email).firstResultOptional();

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			UserMapper.INSTANCE.partialUpdate(updateInput, user);
			user.persist();
			return Response.ok(UserMapper.INSTANCE.toDto(user)).build();
		}

		return Response.status(Response.Status.NOT_FOUND).entity(new MessageResponse(
			"Email address not found.",
			false
		)).build();
	}
}
