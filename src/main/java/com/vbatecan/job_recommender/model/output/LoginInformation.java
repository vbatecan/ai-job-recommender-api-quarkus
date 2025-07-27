package com.vbatecan.job_recommender.model.output;

import com.vbatecan.job_recommender.model.dto.UserDTO;
import com.vbatecan.job_recommender.model.enumeration.UserRole;

public record LoginInformation(
	UserDTO user,
	String token,
	UserRole role
) {
}
