package com.vbatecan.job_recommender.model.input;

import com.vbatecan.job_recommender.model.dto.CompanyDTO;
import com.vbatecan.job_recommender.model.dto.ResumeDTO;
import com.vbatecan.job_recommender.model.dto.UserSkillDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserUpdateInput(
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
	String email
) {
}
