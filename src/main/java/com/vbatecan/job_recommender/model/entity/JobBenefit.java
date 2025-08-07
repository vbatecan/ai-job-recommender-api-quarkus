package com.vbatecan.job_recommender.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "job_benefits")
public class JobBenefit extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@JoinColumn(name = "job_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Job job;

	@Size(max = 255)
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Job getJob() {
		return job;
	}

	public JobBenefit setJob(Job job) {
		this.job = job;
		return this;
	}
}