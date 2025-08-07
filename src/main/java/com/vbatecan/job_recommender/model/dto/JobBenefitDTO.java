package com.vbatecan.job_recommender.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.vbatecan.job_recommender.model.entity.JobBenefit}
 */
public record JobBenefitDTO( Long id, @NotNull @Size(max = 255) String name ) implements Serializable {
}