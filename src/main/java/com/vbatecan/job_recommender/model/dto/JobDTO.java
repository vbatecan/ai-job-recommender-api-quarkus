package com.vbatecan.job_recommender.model.dto;

import com.vbatecan.job_recommender.model.enumeration.JobType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link com.vbatecan.job_recommender.model.entity.Job}
 */
public record JobDTO(
	Long id,

	@NotNull
	UserDTO employer,

	@NotNull
	@Size(max = 255)
	String title,

	String description,
	Integer salaryStart,
	Integer salaryEnd,

	@NotNull
	JobType jobType,

	Instant postedAt,
	Instant expiresAt,
	Set<ApplicationDTO> applications,
	Set<JobTagDTO> jobTags,
	Set<JobBenefitDTO> jobBenefits,
	Set<JobSkillsDesiredDTO> jobSkills,
	Set<JobRequirementDTO> jobRequirements ) implements Serializable {
}