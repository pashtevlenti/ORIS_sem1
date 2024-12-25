create sequence users_sequence;
create sequence sport_sequence;
create sequence coach_sequence;
create sequence group_sportsman_sequence;
create sequence schedule_training_sequence;
create sequence sportsman_sequence;
create sequence worker_sequence;

create table users(
    id bigint not null default nextval('users_sequence'),
    name varchar(255) not null,
    gender char,
    age int,
    phone varchar(255),
    address varchar(255),
    login varchar(255) not null,
    password varchar(255) not null,

    constraint users_id_pk primary key (id),
    constraint users_login_uq unique (login)
);

create table sport(
    id bigint not null default nextval('sport_sequence'),
    name varchar(255) not null,

    constraint sport_id_pk primary key (id)
);

create table coach(
    id bigint not null default nextval('coach_sequence'),
    sport_id bigint,
    user_id bigint not null,

    constraint coach_id_pk primary key (id),
    constraint coach_sport_id_fk foreign key (sport_id) references sport(id) on delete cascade,
    constraint coach_user_id_fk foreign key (user_id) references users(id) on delete cascade
);

create table group_sportsman(
    id bigint not null default nextval('group_sportsman_sequence'),
    group_name varchar(255),
    coach_id bigint not null,

    constraint group_sportsman_id_pk primary key (id),
    constraint group_sportsman_coach_id_fk foreign key (coach_id) references coach(id) on delete cascade
);
create table schedule_training(
    id bigint not null default nextval('schedule_training_sequence'),
    day_of_week varchar(255),
    time varchar(255),
    coach_id bigint not null,

    constraint schedule_training_id_pk primary key (id),
    constraint schedule_training_coach_id_fk foreign key (coach_id) references coach(id) on delete cascade
);

create table sportsman(
    id bigint not null default nextval('sportsman_sequence'),
    rank varchar(255),
    user_id bigint not null,

    constraint sportsman_id_pk primary key (id),
    constraint sportsman_user_id_fk foreign key (user_id) references users(id) on delete cascade
);

create table worker(
    id bigint not null default nextval('worker_sequence'),
    post varchar(255),
    user_id bigint not null,

    constraint worker_id_pk primary key (id),
    constraint worker_user_id_fk foreign key (user_id) references users(id) on delete cascade
);

create table sportsman_coach(
    sportsman_id bigint not null,
    coach_id bigint not null,

    constraint sportsman_coach_sportsman_id_fk foreign key (sportsman_id) references sportsman(id) on delete cascade,
    constraint sportsman_coach_coach_id_fk foreign key (coach_id) references coach(id) on delete cascade
);
--
-- create table sportsman_schedule_training(
--     sportsman_id bigint not null,
--     schedule_training_id bigint not null,
--
--     constraint sportsman_coach_sportsman_id_fk foreign key (sportsman_id) references sportsman(id) on delete cascade,
--     constraint sportsman_coach_schedule_training_id_fk foreign key (schedule_training_id) references schedule_training(id) on delete cascade
-- );

create table group_sportsman_sportsman(
    sportsman_id bigint not null,
    group_sportsman_id bigint not null,


    constraint group_sportsman_sportsman_sportsman_id_fk foreign key (sportsman_id) references sportsman(id) on delete cascade,
    constraint group_sportsman_sportsman_group_sportsman_id_fk foreign key (group_sportsman_id) references group_sportsman(id) on delete cascade
);
create table schedule_training_group_sportsman(
    schedule_training_id bigint not null,
    group_sportsman_id bigint not null,


    constraint schedule_training_group_sportsman_schedule_training_id_fk foreign key (schedule_training_id) references schedule_training(id) on delete cascade,
    constraint schedule_training_group_sportsman_group_sportsman_id_fk foreign key (group_sportsman_id) references group_sportsman(id) on delete cascade
);