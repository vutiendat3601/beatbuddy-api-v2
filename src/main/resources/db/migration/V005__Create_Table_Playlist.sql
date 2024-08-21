CREATE TABLE playlist (
	pk_id uuid DEFAULT gen_random_uuid() NOT NULL,
	id varchar(16) NOT NULL,
	urn varchar(100) NOT NULL,
	"name" varchar(255) NOT NULL,
	thumbnail varchar(255) NULL,
	"description" text NULL,
	is_public bool DEFAULT true NOT NULL,
  item_urns text DEFAULT ''::text NOT NULL,
	owner_id varchar(255) NOT NULL,
  ref_code varchar(100) NULL,
	tags text DEFAULT ''::text NOT NULL,
	tsv tsvector NULL,
	created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL,
	CONSTRAINT playlist_pk PRIMARY KEY (pk_id),
	CONSTRAINT playlist_id_key UNIQUE (id),
	CONSTRAINT playlist_urn_key UNIQUE (urn)
);

CREATE INDEX playlist_tsv_idx ON public.playlist USING gin (tsv);

-- Functions
CREATE OR REPLACE FUNCTION public.playlist_update_tsv_column()
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
create trigger playlist_before_insert_trigger before
insert
    on
    public.playlist for each row execute function playlist_update_tsv_column();
create trigger playlist_before_update_trigger before
update
    on
    public.playlist for each row execute function playlist_update_tsv_column();
