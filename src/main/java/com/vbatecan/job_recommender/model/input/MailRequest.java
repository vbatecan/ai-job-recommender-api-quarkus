package com.vbatecan.job_recommender.model.input;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MailRequest(
	@NotNull
	@Size(min = 1, max = 100)
	@NotEmpty
	String to,

	@NotNull
	@Size(min = 1, max = 250)
	@NotEmpty
	String subject,

	@Size(min = 1, max = 10000)
	String body ) {
}
