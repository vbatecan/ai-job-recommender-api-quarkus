package com.vbatecan.job_recommender.model.entity;

import com.vbatecan.job_recommender.model.enumeration.JobType;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
public class Job extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "employer_id", nullable = false)
	private User employer;

	@Size(max = 255)
	@NotNull
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "salary_start")
	private Integer salaryStart;

	@Column(name = "salary_end")
	private Integer salaryEnd;

	@NotNull
	@Column(name = "job_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private JobType jobType;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "posted_at")
	private Instant postedAt;

	@Column(name = "expires_at")
	private Instant expiresAt;

	@OneToMany(mappedBy = "job")
	private Set<Application> applications = new LinkedHashSet<>();

	@OneToMany(mappedBy = "job")
	private Set<JobTag> jobTags = new LinkedHashSet<>();

	@OneToMany
	@JoinColumn(name = "job_id")
	private Set<JobBenefit> jobBenefits = new LinkedHashSet<>();

	@OneToMany
	@JoinColumn(name = "job_id")
	private Set<JobRequirement> jobRequirements = new LinkedHashSet<>();

	@OneToMany
	@JoinColumn(name = "job_id")
	private Set<JobSkillsDesired> jobSkills = new LinkedHashSet<>();

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "created_at")
	private Instant createdAt;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "last_modified_at")
	private Instant lastModifiedAt;


	public User getEmployer() {
		return employer;
	}

	public void setEmployer(User employer) {
		this.employer = employer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSalaryStart() {
		return salaryStart;
	}

	public void setSalaryStart(Integer salaryStart) {
		this.salaryStart = salaryStart;
	}

	public Integer getSalaryEnd() {
		return salaryEnd;
	}

	public void setSalaryEnd(Integer salaryEnd) {
		this.salaryEnd = salaryEnd;
	}

	public Long getId() {
		return id;
	}

	public Job setId(Long id) {
		this.id = id;
		return this;
	}

	public JobType getJobType() {
		return jobType;
	}

	public Job setJobType(JobType jobType) {
		this.jobType = jobType;
		return this;
	}

	public Instant getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Instant postedAt) {
		this.postedAt = postedAt;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Set<JobBenefit> getJobBenefits() {
		return jobBenefits;
	}

	public Job setJobBenefits(Set<JobBenefit> jobBenefits) {
		this.jobBenefits = jobBenefits;
		return this;
	}

	public Set<JobRequirement> getJobRequirements() {
		return jobRequirements;
	}

	public Job setJobRequirements(Set<JobRequirement> jobRequirements) {
		this.jobRequirements = jobRequirements;
		return this;
	}

	public Set<JobSkillsDesired> getJobSkills() {
		return jobSkills;
	}

	public Job setJobSkills(Set<JobSkillsDesired> jobSkills) {
		this.jobSkills = jobSkills;
		return this;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Instant lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}

	public Set<JobTag> getJobTags() {
		return jobTags;
	}

	public void setJobTags(Set<JobTag> jobTags) {
		this.jobTags = jobTags;
	}

}