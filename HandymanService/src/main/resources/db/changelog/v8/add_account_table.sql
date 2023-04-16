create table if not exists account(
    id bigserial primary key,
    user_id bigint not null,
    card_id text not null,
    payment_system text not null,
    constraint user_fkey foreign key (user_id) references h_user (id)
);