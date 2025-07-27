package com.vbatecan.job_recommender.exception;

public class ExpiredTokenException extends RuntimeException {
	public ExpiredTokenException(String message) {
		super(message);
	}
}
