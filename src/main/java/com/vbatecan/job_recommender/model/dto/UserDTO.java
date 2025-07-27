package com.vbatecan.job_recommender.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link com.vbatecan.job_recommender.model.entity.User}
 */
public record UserDTO(
	Long id,
	@NotNull String role,
	@Null
	CompanyDTO company,

	Set<ResumeDTO> resume,
	Set<UserSkillDTO> userSkills,

	@NotNull
	@Size(max = 64)
	String firstName,

	@Size(max = 64)
	String middleName,

	@NotNull
	@Size(max = 64)
	String lastName,

	@Size(max = 128)
	String course,

	@Size(max = 4)
	String graduationYear,

	@NotNull
	@Email
	@Size(max = 128)
	String email,

	@NotNull Boolean isActive,
	Instant lastLogin ) implements Serializable {
}