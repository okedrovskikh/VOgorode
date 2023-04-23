alter table account
    add constraint account_u_type_email_telephone_constraint unique (u_type, email, telephone);