create extension if not exists postgis;

create table if not exists field
(
    id         bigserial primary key,
    address    text             not null,
    latitude   double precision not null,
    longitude  double precision not null,
    area       geometry         not null,
    fielder_id bigint           not null,
    constraint fielder_fkey foreign key (fielder_id) references fielder (id)
);