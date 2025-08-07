package com.vbatecan.job_recommender.impl;

import com.vbatecan.job_recommender.service.TemplateService;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@ApplicationScoped
public class TemplateServiceImpl implements TemplateService {

	@Override
	public String loadTemplate(String templateName) {
		try ( InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("templates/" + templateName);
		      Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name()) ) {
			return scanner.useDelimiter("\\A").next();
		} catch ( IOException | NullPointerException e ) {
			throw new UncheckedIOException("Template not found: " + templateName, new IOException(e));
		}
	}
}
