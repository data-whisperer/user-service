CREATE TABLE IF NOT EXISTS public.user_details
(
    id uuid NOT NULL,
    full_name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    created_on timestamp without time zone NOT NULL,
    CONSTRAINT user_details_pkey PRIMARY KEY (id),
    CONSTRAINT user_details_email_key UNIQUE (email)
);