package com.vbatecan.job_recommender.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

/**
 * DTO for {@link com.vbatecan.job_recommender.model.entity.Resume}
 */
public record ResumeDTO(
	Long id,
	@NotNull(message = "Name of resume cannot be null.")
	@Size(message = "Name of a resume should not be more than 128 characters.", max = 128)
	String name,
	@Size(message = "Downloadable URL shouldn't be more than 255 characters.", max = 255)
	@URL(message = "The downloadable url should be valid link.")
	String downloadableUrl,
	Boolean isPrimary ) implements Serializable {
}