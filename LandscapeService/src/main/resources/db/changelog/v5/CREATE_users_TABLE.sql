create table if not exists users
(
    id bigserial primary key,
    u_type text not null,
    u_login varchar(42) not null,
    email text not null,
    telephone varchar(22) not null,
    creation_date timestamp not null,
    update_date timestamp not null
);
