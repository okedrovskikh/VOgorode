alter table fielder
    add constraint fielder_email_and_telephone_constraint unique (email, telephone);