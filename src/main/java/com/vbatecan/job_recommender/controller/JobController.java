package com.vbatecan.job_recommender.controller;

import com.vbatecan.job_recommender.mapping.JobMapper;
import com.vbatecan.job_recommender.model.entity.Job;
import com.vbatecan.job_recommender.model.input.PageRequest;
import com.vbatecan.job_recommender.model.output.MessageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Path("/api/v1/jobs")
public class JobController {

	@Inject
	JobMapper jobMapper;

	@GET
	@Path("")
	public Response listAll(@BeanParam @Valid PageRequest pageRequest) {
		List<Job> jobs = Job.findAll().page(pageRequest.toPage()).list();
		return Response.ok(
			jobs.stream()
				.map(jobMapper::toDto)
				.toList()
		).build();
	}

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		if ( id <= 0 ) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponse(
				"Invalid ID provided",
				false
			)).build();
		}

		Optional<Job> jobOptional = Job.findByIdOptional(id);

		if ( jobOptional.isEmpty() ) {
			return Response.status(Response.Status.NOT_FOUND).entity(new MessageResponse(
				"Job with ID " + id + " not found",
				false
			)).build();
		}

		return Response.ok(
			jobMapper.toDto(jobOptional.get())
		).build();
	}
}
