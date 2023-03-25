create table if not exists site
(
    id uuid default uuid_generate_v4() primary key,
    latitude double precision not null,
    longitude double precision not null
);
