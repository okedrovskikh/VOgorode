alter table h_user
    add constraint h_user_email_and_telephone_constraint unique (email, telephone);