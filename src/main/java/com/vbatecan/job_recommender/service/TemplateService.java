package com.vbatecan.job_recommender.service;

import jakarta.enterprise.context.ApplicationScoped;

public interface TemplateService {

	String loadTemplate(String templateName);
}
