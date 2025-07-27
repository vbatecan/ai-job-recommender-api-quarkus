package com.vbatecan.job_recommender.model.entity;

import com.vbatecan.job_recommender.model.enumeration.ApplicationStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "applications")
public class Application extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id")
	private Job job;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "candidate_id")
	private User candidate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_id")
	private Resume resume;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "applied_at")
	private Instant appliedAt;

	@Column(name = "status", length = 255)
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getCandidate() {
		return candidate;
	}

	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public Instant getAppliedAt() {
		return appliedAt;
	}

	public void setAppliedAt(Instant appliedAt) {
		this.appliedAt = appliedAt;
	}

	public Long getId() {
		return id;
	}

	public Application setId(Long id) {
		this.id = id;
		return this;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public Application setStatus(ApplicationStatus status) {
		this.status = status;
		return this;
	}
}