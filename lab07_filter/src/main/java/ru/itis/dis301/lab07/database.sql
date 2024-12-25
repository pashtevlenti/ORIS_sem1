
create table users(
                      id bigint not null default nextval('users_sequence'),
                      login varchar(255) not null,
                      password varchar(255) not null ,

                      constraint users_id_pk primary key (id),
                      constraint users_login_uq unique (login));

insert into users (login,password)
values ('Pasha','$2a$10$y.9R9A27mORKGo3ct8VcpOpYJH7KillIwPpCDPZyh7Q.fkD7l8kBa');

insert into users (login,password)
values ('Lesha','$2a$10$zvfL4j2T2xuKdkH2aOO.UusSp/caZL3TwVA8dm6RywidKT1aIdR.S');

insert into users (login,password)
values ('Andrey','$2a$10$KYdMjH8xnf5Exgppgs5LR.JzB5US7mzbeSw6XU.LEnWoZ2lGzgaSC');

insert into users (login,password)
values ('Sasha','$2a$10$An.DgUhFlqx5WVCFAzE.5.5IPymAPj5ucbuozav8A4cCCZ0.diC8i');

insert into users (login,password)
values ('Fedor','$2a$10$NMpA8y.a8c78/GRmyj82Ru06rE/J5eb7chP16exQB4tG/a7vRY.k.');