create table if not exists h_user(
    id bigserial primary key,
    name text not null,
    surname text not null,
    skills text not null,
    email text not null,
    telephone text not null,
    photo bytea not null
)