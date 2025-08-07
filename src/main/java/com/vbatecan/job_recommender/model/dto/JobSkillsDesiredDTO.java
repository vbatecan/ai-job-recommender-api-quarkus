package com.vbatecan.job_recommender.model.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link com.vbatecan.job_recommender.model.entity.JobSkillsDesired}
 */
public record JobSkillsDesiredDTO( Long id, @NotNull UserSkillDTO userSkill ) implements Serializable {
}