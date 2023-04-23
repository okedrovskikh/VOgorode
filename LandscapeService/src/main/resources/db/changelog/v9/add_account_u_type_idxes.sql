create index if not exists account_rancher_idx on account using hash (u_type) where u_type = 'rancher';
create index if not exists account_rancher_idx on account using hash (u_type) where u_type = 'handyman';
create index if not exists account_rancher_idx on account using hash (u_type) where u_type = 'landscape';
