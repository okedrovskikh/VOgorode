do '
begin
create type work as enum (''shovel'', ''plant'', ''water'', ''sow'');
exception
when duplicate_object THEN null;
end;
';

create table if not exists l_order
(
    id         bigserial primary key,
    garden_id  uuid    not null,
    worker_id  uuid,
    works      work[]    not null,
    status     text      not null,
    created_at timestamp not null
);