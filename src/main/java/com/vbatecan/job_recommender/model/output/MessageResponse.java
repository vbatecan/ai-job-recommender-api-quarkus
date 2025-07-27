package com.vbatecan.job_recommender.model.output;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.DefaultValue;

public record MessageResponse(
	@Size(min = 1, max = Integer.MAX_VALUE)
	@NotBlank
	String message,
	@NotBlank
	Boolean success
) {
}
