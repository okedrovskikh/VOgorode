create type user_types as enum('handyman', 'landscape', 'rancher');

alter table users alter column u_type type user_types using u_type::text::user_types;
