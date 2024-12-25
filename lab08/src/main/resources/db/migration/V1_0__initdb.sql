
create sequence users_seq;

create table users (
    id bigint primary key default nextval('users_seq'),
    username character varying(50),
    password character varying(100)
);