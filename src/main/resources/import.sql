CREATE TABLE job_tags
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL AUTO_INCREMENT,
    job_id           BIGINT                               NOT NULL,
    name             VARCHAR(64) UNIQUE                NOT NULL,
    created_at       TIMESTAMP DEFAULT now(),
    last_modified_at TIMESTAMP DEFAULT now()
);

CREATE TABLE companies
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL AUTO_INCREMENT,
    name             VARCHAR(128)                      NOT NULL,
    industry         VARCHAR(255),
    website_url      VARCHAR(255),
    logo_url         VARCHAR(255),
    created_at       TIMESTAMP DEFAULT now(),
    last_modified_at TIMESTAMP DEFAULT now()
);

CREATE TABLE jobs
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    employer_id      BIGINT                                  NOT NULL,
    title            VARCHAR(255)                         NOT NULL,
    description      TEXT,
    salary_start     INT,
    salary_end       INT,
    job_type         ENUM ('REMOTE', 'ON_SITE', 'HYBRID') NOT NULL,
    posted_at        TIMESTAMP DEFAULT now(),
    expires_at       TIMESTAMP,
    created_at       TIMESTAMP DEFAULT now(),
    last_modified_at TIMESTAMP DEFAULT now()
);

CREATE TABLE applications
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id       BIGINT,
    candidate_id BIGINT,
    resume_id    BIGINT,
    applied_at   TIMESTAMP DEFAULT now(),
    status       ENUM ('PENDING', 'REVIEWED', 'HIRED', 'FAILED', 'OTHER')
);

CREATE TABLE user_skills
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id          BIGINT                               NOT NULL,
    name             VARCHAR(128) UNIQUE               NOT NULL,
    created_at       TIMESTAMP DEFAULT now(),
    last_modified_at TIMESTAMP DEFAULT now()
);

CREATE TABLE resumes
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id          BIGINT                               NOT NULL,
    name             VARCHAR(128) UNIQUE               NOT NULL,
    downloadable_url VARCHAR(255)                      NOT NULL,
    is_primary       BOOLEAN,
    created_at       TIMESTAMP DEFAULT now(),
    last_modified_at TIMESTAMP DEFAULT now()
);

CREATE TABLE users
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    role             ENUM ('CANDIDATE', 'EMPLOYER')    NOT NULL,
    company_id       BIGINT                                        DEFAULT null,
    first_name       VARCHAR(64)                       NOT NULL,
    middle_name      VARCHAR(64),
    last_name        VARCHAR(64)                       NOT NULL,
    course           VARCHAR(128),
    graduation_year  CHAR(4),
    email            VARCHAR(128) UNIQUE               NOT NULL,
    password         CHAR(60)                          NOT NULL,
    is_active        BOOLEAN                           NOT NULL DEFAULT true,
    last_login       TIMESTAMP,
    created_at       TIMESTAMP                                  DEFAULT now(),
    last_modified_at TIMESTAMP                                  DEFAULT now()
);

CREATE TABLE forgot_password_codes
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id          BIGINT UNIQUE     NOT NULL,
    code             CHAR(6) UNIQUE NOT NULL,
    expires_at       TIMESTAMP      NOT NULL,
    used             BOOLEAN   DEFAULT false,
    created_at       TIMESTAMP DEFAULT now(),
    last_modified_at TIMESTAMP DEFAULT now()
);

ALTER TABLE applications
    ADD FOREIGN KEY (job_id) REFERENCES jobs (id);

ALTER TABLE applications
    ADD FOREIGN KEY (candidate_id) REFERENCES users (id);

ALTER TABLE applications
    ADD FOREIGN KEY (resume_id) REFERENCES resumes (id);

ALTER TABLE forgot_password_codes
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE resumes
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_skills
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE jobs
    ADD FOREIGN KEY (employer_id) REFERENCES users (id);

ALTER TABLE job_tags
    ADD FOREIGN KEY (job_id) REFERENCES jobs (id);

ALTER TABLE users
    ADD FOREIGN KEY (company_id) REFERENCES companies (id);
