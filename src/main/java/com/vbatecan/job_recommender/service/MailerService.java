package com.vbatecan.job_recommender.service;

import com.vbatecan.job_recommender.model.input.MailRequest;

public interface MailerService {

	void sendEmail(MailRequest mailRequest);
}
