create table if not exist field(
    id bigserial primary key,
    address text not null,
    latitude float16 not null,
    longitude float16 not null,
    area geometry not null
)