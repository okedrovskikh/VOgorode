create table if not exists site
(
    id        uuid primary key default uuid_generate_v4(),
    latitude  double precision not null,
    longitude double precision not null
);
