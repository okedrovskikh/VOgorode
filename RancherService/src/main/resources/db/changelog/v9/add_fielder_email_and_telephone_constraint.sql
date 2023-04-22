alter table fielder
    add constraint email_and_telephone_constraint unique (email, telephone);
alter table fielder
    add constraint email_constraint unique (email);
