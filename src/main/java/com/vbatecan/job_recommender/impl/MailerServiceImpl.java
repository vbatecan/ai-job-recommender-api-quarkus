package com.vbatecan.job_recommender.impl;

import com.vbatecan.job_recommender.model.input.MailRequest;
import com.vbatecan.job_recommender.service.MailerService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MailerServiceImpl implements MailerService {
	@Inject
	Mailer mailer;

	@Override
	public void sendEmail(MailRequest mailRequest) {
		Mail mail = Mail.withHtml(mailRequest.to(), mailRequest.subject(), mailRequest.body());
		mailer.send(mail);
	}
}
