CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 1. Userdetails
CREATE TABLE userdetails (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fullname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Access Role
CREATE TABLE access_role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. User Permission
CREATE TABLE user_permission (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- 4. AccessRole2Permission
CREATE TABLE accessrole2permission (
    access_role_id UUID NOT NULL,
    user_permission_id UUID NOT NULL,
    PRIMARY KEY (access_role_id, user_permission_id),
    FOREIGN KEY (access_role_id) REFERENCES access_role(id),
    FOREIGN KEY (user_permission_id) REFERENCES user_permission(id)
);

-- 5. File Details
CREATE TABLE file_details (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    mime_type VARCHAR(100),
    location TEXT NOT NULL
);

-- 6. Resume
CREATE TABLE resume (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    resume_file UUID NOT NULL,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES userdetails(id),
    FOREIGN KEY (resume_file) REFERENCES file_details(id)
);

-- 7. Skill
CREATE TABLE skill (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL
);

-- 8. Skill Level
CREATE TABLE skill_level (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL
);

-- 9. Skill Level Description
CREATE TABLE skill_level_description (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    skill_id UUID NOT NULL,
    skill_level_id UUID NOT NULL,
    description TEXT,
    FOREIGN KEY (skill_id) REFERENCES skill(id),
    FOREIGN KEY (skill_level_id) REFERENCES skill_level(id),
    UNIQUE (skill_id, skill_level_id)
);

-- 10. User Skills
CREATE TABLE user_skills (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    skill_id UUID NOT NULL,
    skill_level_id UUID NOT NULL,
    experience INT,
    FOREIGN KEY (user_id) REFERENCES userdetails(id),
    FOREIGN KEY (skill_id) REFERENCES skill(id),
    FOREIGN KEY (skill_level_id) REFERENCES skill_level(id),
    UNIQUE (user_id, skill_id)
);
