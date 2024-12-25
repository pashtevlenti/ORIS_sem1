
create sequence users_seq;

create table users (
    id bigint primary key default nextval('users_seq'),
    username character varying(50),
    password character varying(100),
    userrole character varying(50)
);

insert into users (username, password) values ('admin', '$2a$10$eVd7gUyIfgYKYWW0/eUwoek9h1LqwMXDgzPK7liSY79CtzPCDjTNi');

create sequence client_seq;

create table client (
    id bigint primary key default nextval('client_seq'),
    name character varying(50),
    email character varying(100)
);

create table client_info (
    id bigint primary key,
    phone character varying(50),
    address character varying(50),
    passport character varying(50),
    constraint fk_client_info foreign key (id) references client(id)
);

