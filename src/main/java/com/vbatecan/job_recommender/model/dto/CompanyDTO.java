package com.vbatecan.job_recommender.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

/**
 * DTO for {@link com.vbatecan.job_recommender.model.entity.Company}
 */
public record CompanyDTO(
	Long id,

	@NotNull
	@Size(message = "Company name cannot be empty. It should be also be less than 128 characters.", min = 1, max = 128)
	@NotBlank(message = "Company name cannot be blank.")
	String name,

	@Size(message = "Company industry shouldn't be more than 255 characters.", max = 255)
	String industry,

	@Size(max = 255)
	@URL(message = "Company website should be a valid URL.")
	String websiteUrl,

	@Size(max = 255)
	@URL(message = "Company Logo URL should be a valid URL.")
	String logoUrl ) implements Serializable {
}