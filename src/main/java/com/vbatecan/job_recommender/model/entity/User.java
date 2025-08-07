package com.vbatecan.job_recommender.model.entity;

import com.vbatecan.job_recommender.model.enumeration.UserRole;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	@Roles
	private UserRole role;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private Set<UserSkill> userSkills;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private Set<Resume> resume;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Size(max = 64)
	@NotNull
	@Column(name = "first_name", nullable = false, length = 64)
	private String firstName;

	@Size(max = 64)
	@Column(name = "middle_name", length = 64)
	private String middleName;

	@Size(max = 64)
	@NotNull
	@Column(name = "last_name", nullable = false, length = 64)
	private String lastName;

	@Size(max = 128)
	@Column(name = "course", length = 128)
	private String course;

	@Size(max = 4)
	@Column(name = "graduation_year", length = 4)
	private String graduationYear;

	@Size(max = 128)
	@NotNull
	@Column(name = "email", nullable = false, length = 128)
	@Username
	private String email;

	@Size(max = 60)
	@Column(name = "password", nullable = false, length = 60)
	@Password
	private String password;

	@NotNull
	@ColumnDefault("1")
	@Column(name = "is_active", nullable = false)
	private Boolean isActive = false;

	@Column(name = "last_login")
	private Instant lastLogin;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "created_at", updatable = false)
	private Instant createdAt;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "last_modified_at")
	private Instant lastModifiedAt;

	public static Optional<User> get(Long id) {
		return User.findByIdOptional(id);
	}

	public static Optional<User> getByEmail(String email) {
		return User.find("email", email).firstResultOptional();
	}

	public Set<Resume> getResume() {
		return resume;
	}

	public User setResume(Set<Resume> resume) {
		this.resume = resume;
		return this;
	}

	@PrePersist
	void onCreate() {
		createdAt = Instant.now();
		lastModifiedAt = Instant.now();
	}

	@PreUpdate
	void onUpdate() {
		lastModifiedAt = Instant.now();
	}

	public @NotNull Set<UserSkill> getUserSkills() {
		return userSkills;
	}

	public User setUserSkills(Set<UserSkill> userSkills) {
		this.userSkills = userSkills;
		return this;
	}

	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public UserRole getRole() {
		return role;
	}

	public User setRole(UserRole role) {
		this.role = role;
		return this;
	}

	public Boolean getActive() {
		return isActive;
	}

	public User setActive(Boolean active) {
		isActive = active;
		return this;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getGraduationYear() {
		return graduationYear;
	}

	public void setGraduationYear(String graduationYear) {
		this.graduationYear = graduationYear;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
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

}