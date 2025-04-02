CREATE TABLE IF NOT EXISTS public.user_permission
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    description character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_permission_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.access_role
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    created_on timestamp without time zone NOT NULL,
    CONSTRAINT access_role_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.access_role_user_permission
(
    access_role_id uuid NOT NULL,
    user_permission_id uuid NOT NULL,
    CONSTRAINT access_role_user_permission_pkey PRIMARY KEY (access_role_id, user_permission_id),
    CONSTRAINT access_role_user_permission_access_role_id_fkey FOREIGN KEY (access_role_id)
        REFERENCES public.access_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT access_role_user_permission_user_permission_id_fkey FOREIGN KEY (user_permission_id)
        REFERENCES public.user_permission (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.file_details
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    mime_type character varying COLLATE pg_catalog."default" NOT NULL,
    path character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT file_details_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.user_details
(
    id uuid NOT NULL,
    full_name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    created_on timestamp without time zone NOT NULL,
    picture uuid,
    access_role_id uuid NOT NULL,
    CONSTRAINT user_details_pkey PRIMARY KEY (id),
    CONSTRAINT user_details_email_key UNIQUE (email),
    CONSTRAINT user_details_access_role_id_fkey FOREIGN KEY (access_role_id)
        REFERENCES public.access_role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT user_details_picture_fkey FOREIGN KEY (picture)
        REFERENCES public.file_details (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE IF NOT EXISTS public.resume
(
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    file_id uuid NOT NULL,
    upload_date timestamp without time zone NOT NULL,
    CONSTRAINT resume_pkey PRIMARY KEY (id),
    CONSTRAINT resume_file_id_fkey FOREIGN KEY (file_id)
        REFERENCES public.file_details (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT resume_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.user_details (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.skill
(
    id uuid NOT NULL,
    name name COLLATE pg_catalog."C" NOT NULL,
    CONSTRAINT skill_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.skill_level
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT skill_level_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.skill_details
(
    id uuid NOT NULL,
    skill_id uuid NOT NULL,
    skill_level_id uuid NOT NULL,
    description character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT skill_level_description_pkey PRIMARY KEY (id),
    CONSTRAINT skill_level_description_skill_id_fkey FOREIGN KEY (skill_id)
        REFERENCES public.skill (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT skill_level_description_skill_level_id_fkey FOREIGN KEY (skill_level_id)
        REFERENCES public.skill_level (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE IF NOT EXISTS public.user_skills
(
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    skill_details uuid NOT NULL,
    experience integer NOT NULL,
    CONSTRAINT user_skills_pkey PRIMARY KEY (id),
    CONSTRAINT user_skills_skill_details_fkey FOREIGN KEY (skill_details)
        REFERENCES public.skill_details (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT user_skills_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.user_details (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);