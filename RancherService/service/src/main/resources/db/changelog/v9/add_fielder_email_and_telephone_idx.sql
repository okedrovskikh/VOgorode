create unique index if not exists fielder_email_and_telephone_idx on fielder (email, (telephone is null)) where telephone is null;
