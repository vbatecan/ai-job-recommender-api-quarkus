package com.vbatecan.job_recommender.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ForgotPasswordRequest(
	@Email
	@Size(min = 1, max = 128)
	String email
) {
}
