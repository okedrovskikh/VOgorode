create type paymentsystem as enum ('mastercard', 'visa', 'mir', 'unionpay');

create table if not exists bank_account
(
    id             bigserial primary key,
    user_id        bigint,
    card_id        text          not null,
    payment_system paymentsystem not null,
    constraint user_fkey foreign key (user_id) references h_user (id)
);