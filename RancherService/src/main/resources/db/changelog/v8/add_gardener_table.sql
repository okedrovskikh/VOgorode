create table if not exist gardener(
    id bigserial primary key,
    name text not null,
    surname text not null,
    telephone text
)