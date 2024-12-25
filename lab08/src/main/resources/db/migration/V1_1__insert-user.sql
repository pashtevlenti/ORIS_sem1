insert into users(id, username, password) values
    ((select nextval('users_seq')), 'user', 'password');
