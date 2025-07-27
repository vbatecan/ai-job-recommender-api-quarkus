package com.vbatecan.job_recommender.model.input;

import io.quarkus.panache.common.Page;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PageRequest {

	@QueryParam("page")
	@DefaultValue("0")
	public Integer page;

	@QueryParam("size")
	@DefaultValue("10")
	public Integer size;

	public Page toPage() {
		if ( page < 0 ) {
			page = 0;
		}

		if ( size < 0 ) {
			size = 10;
		}

		return Page.of(page, size);
	}
}
