CREATE TABLE track_artist (
	pk_id uuid DEFAULT gen_random_uuid() NOT NULL,
	track_pk_id uuid NULL,
	artist_pk_id uuid NULL,
	is_active bool NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL,
	CONSTRAINT track_artist_pk PRIMARY KEY (pk_id),
	CONSTRAINT track_artist_track_pk_id_artist_pk_id_key UNIQUE (track_pk_id, artist_pk_id)
);
