create type account_types as enum ('handyman', 'landscape', 'rancher');

alter table account
    alter column u_type type account_types using u_type::text::account_types;
