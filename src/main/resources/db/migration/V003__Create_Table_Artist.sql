CREATE TABLE artist (
	pk_id uuid DEFAULT gen_random_uuid() NOT NULL,
	id varchar(16) NOT NULL,
	urn varchar(100) NOT NULL,
	"name" varchar(255) NOT NULL,
	is_verified bool DEFAULT false NOT NULL,
	is_public bool DEFAULT false NOT NULL,
	real_name varchar(255) NULL,
	birth_date date NULL,
	biography varchar(250) NULL,
	description text NULL,
	nationality varchar(255) NULL,
	thumbnail varchar(255) NULL,
	background varchar(255) NULL,
	total_likes int8 DEFAULT 0 NOT NULL,
	total_views int8 DEFAULT 0 NOT NULL,
	ref_code varchar(100) NULL,
	tags text DEFAULT ''::text NOT NULL,
	tsv tsvector NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL,
	CONSTRAINT artists_pk PRIMARY KEY (pk_id),
	CONSTRAINT artists_id_key UNIQUE (id),
	CONSTRAINT artists_ref_code_key UNIQUE (ref_code),
	CONSTRAINT artists_urn_key UNIQUE (urn)
);
CREATE INDEX artists_tsv_idx ON public.artist USING gin (tsv);

-- Functions
CREATE OR REPLACE FUNCTION public.artist_update_tsv_column()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
  new.tsv = to_tsvector(lower(public.unaccent(new.tags)));
  return new;
END;
$function$
;

-- Triggers
create trigger artist_before_insert_trigger before
insert
    on
    public.artist for each row execute function artist_update_tsv_column();
create trigger artist_before_update_trigger before
update
    on
    public.artist for each row execute function artist_update_tsv_column();
