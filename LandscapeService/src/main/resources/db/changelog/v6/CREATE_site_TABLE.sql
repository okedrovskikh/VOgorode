create table if not exists site
(
    id bigserial primary key,
    latitude double precision not null,
    longitude double precision not null
)