CREATE TABLE track (
	pk_id uuid DEFAULT gen_random_uuid() NOT NULL,
	id varchar(16) NOT NULL,
	urn varchar(100) NOT NULL,
	"name" varchar(255) NOT NULL,
	is_public bool DEFAULT false NOT NULL,
	"description" text NULL,
	released_date varchar(10) NULL,
	duration_sec int4 NULL,
	is_playable bool DEFAULT false NOT NULL,
	thumbnail varchar(255) NULL,
	total_likes int8 DEFAULT 0 NOT NULL,
	total_views int8 DEFAULT 0 NOT NULL,
	total_listens int8 DEFAULT 0 NOT NULL,
	ref_code varchar(100) NULL,
	file_m3u8 varchar(255) NULL,
	tags text DEFAULT ''::text NOT NULL,
	tsv tsvector NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL,
	CONSTRAINT tracks_pk PRIMARY KEY (pk_id),
	CONSTRAINT tracks_id_key UNIQUE (id),
	CONSTRAINT tracks_ref_code_key UNIQUE (ref_code),
	CONSTRAINT tracks_urn_key UNIQUE (urn)
);
CREATE INDEX tracks_tsv_idx ON public.track USING gin (tsv);

-- Functions
CREATE OR REPLACE FUNCTION public.track_update_tsv_column()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
   new.tsv = to_tsvector(lower(unaccent(new.tags)));
  return new;
END;
$function$
;

-- Triggers
create trigger track_before_insert_trigger before
insert
    on
    public.track for each row execute function track_update_tsv_column();
create trigger track_before_update_trigger before
update
    on
    public.track for each row execute function track_update_tsv_column();
