package com.vbatecan.job_recommender.model.dto;

import com.vbatecan.job_recommender.model.enumeration.ApplicationStatus;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.vbatecan.job_recommender.model.entity.Application}
 */
public record ApplicationDTO( Long id, UserDTO candidate, ResumeDTO resume, Instant appliedAt, ApplicationStatus status) implements Serializable {
  }