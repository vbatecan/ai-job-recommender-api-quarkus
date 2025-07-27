package com.vbatecan.job_recommender.model.output;

import com.vbatecan.job_recommender.model.enumeration.UserRole;

public class AuthCheckResponse {
	private boolean authenticated;
	private long expirationTime;
	private UserRole role;

	public AuthCheckResponse(boolean authenticated, long expirationTime, UserRole role) {
		this.authenticated = authenticated;
		this.expirationTime = expirationTime;
		this.role = role;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public AuthCheckResponse setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
		return this;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public AuthCheckResponse setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
		return this;
	}

	public UserRole getRole() {
		return role;
	}

	public AuthCheckResponse setRole(UserRole role) {
		this.role = role;
		return this;
	}
}
