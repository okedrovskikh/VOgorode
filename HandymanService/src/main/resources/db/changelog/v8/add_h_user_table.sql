do '
begin
    create type work as enum (''shovel'', ''plant'', ''water'', ''sow'');
exception
    when duplicate_object THEN null;
end;
';

create table if not exists h_user(
    id bigserial primary key,
    name text not null,
    surname text not null,
    skills work[] not null,
    email text not null,
    telephone text not null,
    photo bytea not null
);