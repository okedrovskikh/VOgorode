create table if not exists area
(
    id bigserial primary key,
    latitude double precision not null,
    longitude double precision not null
)