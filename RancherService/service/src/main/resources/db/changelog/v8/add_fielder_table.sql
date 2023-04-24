create table if not exists fielder
(
    id        bigserial primary key,
    name      text not null,
    surname   text not null,
    email     text not null,
    telephone text
);