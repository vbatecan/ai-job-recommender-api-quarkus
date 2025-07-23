package com.vbatecan.job_recommender.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
	@Email(message = "The email should be valid.")
	@Size(max = 128, message = "Email should not be more than 128 characters.")
	@NotBlank(message = "Email should not be empty.")
	String email,

	@Size(min = 8, max = 64, message = "Password shouldn't be less than 8 and not greater than 64 characters.")
	@NotBlank(message = "Password should not be empty.")
	String password
) {
}
