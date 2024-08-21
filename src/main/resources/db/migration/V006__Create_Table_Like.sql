CREATE TABLE likes (
	pk_id uuid DEFAULT gen_random_uuid() NOT NULL,
	urns text DEFAULT ''::text NOT NULL,
	owner_id varchar(255) NOT NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL,
	CONSTRAINT likes_pk PRIMARY KEY (pk_id),
	CONSTRAINT likes_owner_id_key UNIQUE (owner_id)
);
