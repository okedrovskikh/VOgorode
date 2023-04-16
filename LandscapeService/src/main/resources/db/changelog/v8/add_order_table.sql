create table if not exists order(
    id bigserial primary key,
    garden_id bigint not null,
    user_id bigint not null,
    works text not null,
    status text not null,
    created_at timestamp not null
);