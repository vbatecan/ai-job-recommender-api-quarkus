package com.vbatecan.job_recommender.service;

import com.vbatecan.job_recommender.model.entity.User;

import java.util.Optional;

public interface UserService {

	Optional<User> get(Long id);
}
