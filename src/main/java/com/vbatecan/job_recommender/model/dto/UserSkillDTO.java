package com.vbatecan.job_recommender.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.vbatecan.job_recommender.model.entity.UserSkill}
 */
public record UserSkillDTO(
	Long id,

	@NotNull(message = "The name of a user skill should not be null.")
	@Size(message = "The name of a user skill should not exceed 128 characters", max = 128)
	String name ) implements Serializable {

}