create table if not exists site
(
    id uuid primary key,
    latitude double precision not null,
    longitude double precision not null
);
