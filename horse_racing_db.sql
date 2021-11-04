create table users
(
    id                bigint auto_increment
        primary key,
    name              varchar(45)                 not null,
    email             varchar(70)                 not null,
    bank_account      varchar(20)                 null,
    password          varchar(100)                not null,
    status            varchar(10)    default '1'  null,
    persentage_of_win decimal        default 0    null,
    avatar            varchar(20)                 null,
    date_of_register  date                        not null,
    rates             int            default 0    null,
    surname           varchar(20)                 null,
    role_id           bigint         default 2    null,
    cash              decimal(13, 2) default 0.00 null,
    constraint users_roles__fk
        foreign key (role_id) references roles (id)
);

create index user_id
    on users (id);

create index users_role_id_index
    on users (role_id);
    
    create table permissions
(
    id   bigint auto_increment
        primary key,
    name varchar(30) null
);

INSERT INTO horse_racing_db.permissions (id, name) VALUES (1, 'user_basic');
INSERT INTO horse_racing_db.permissions (id, name) VALUES (2, 'admin_basic');
INSERT INTO horse_racing_db.permissions (id, name) VALUES (3, 'customer_basic');
INSERT INTO horse_racing_db.permissions (id, name) VALUES (4, 'place_bet');
INSERT INTO horse_racing_db.permissions (id, name) VALUES (5, 'place_result');
INSERT INTO horse_racing_db.permissions (id, name) VALUES (6, 'place_ratio');
INSERT INTO horse_racing_db.permissions (id, name) VALUES (7, 'ban_user');

    create table roles
(
    id   bigint auto_increment
        primary key,
    name varchar(30) null
);

INSERT INTO horse_racing_db.roles (id, name) VALUES (1, 'admin');
INSERT INTO horse_racing_db.roles (id, name) VALUES (2, 'user');
INSERT INTO horse_racing_db.roles (id, name) VALUES (3, 'customer');



create table role_permissions
(
    role_id       bigint not null,
    permission_id bigint not null,
    constraint role_permissions_permission__fk
        foreign key (permission_id) references permissions (id),
    constraint role_permissions_roles__fk
        foreign key (role_id) references roles (id)
);

create index role_permissions_permission_id_index
    on role_permissions (permission_id);

create index role_permissions_role_id_index
    on role_permissions (role_id);

INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (3, 3);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (3, 4);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (1, 5);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (1, 6);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (1, 7);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (3, 5);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (3, 6);
INSERT INTO horse_racing_db.role_permissions (role_id, permission_id) VALUES (2, 4);

create table horses
(
    id                 bigint auto_increment
        primary key,
    name               varchar(20)    not null,
    sex                varchar(7)     not null,
    weight             decimal(10, 2) not null,
    breed              varchar(30)    not null,
    age                int            not null,
    status             varchar(20)    not null,
    perсentage_of_wins decimal(10, 2) not null,
    participation      int            not null,
    jockey             varchar(30)    null
);

INSERT INTO horse_racing_db.horses (id, name, sex, weight, breed, age, status, perсentage_of_wins, participation, jockey) VALUES (1, 'Тень', 'male', 420.00, 'Аппалуза', 4, 'true', 88.00, 130, 'Блэк');
INSERT INTO horse_racing_db.horses (id, name, sex, weight, breed, age, status, perсentage_of_wins, participation, jockey) VALUES (2, 'Плотва', 'male', 400.00, 'Мустанг', 6, 'true', 76.00, 50, 'Грин');
INSERT INTO horse_racing_db.horses (id, name, sex, weight, breed, age, status, perсentage_of_wins, participation, jockey) VALUES (3, 'Зубр', 'male', 430.00, 'Морган', 3, 'true', 52.00, 4, 'Уайт');
INSERT INTO horse_racing_db.horses (id, name, sex, weight, breed, age, status, perсentage_of_wins, participation, jockey) VALUES (4, 'Синеглазка', 'female', 270.00, 'Шайр', 4, 'true', 66.00, 30, 'Рэд');
INSERT INTO horse_racing_db.horses (id, name, sex, weight, breed, age, status, perсentage_of_wins, participation, jockey) VALUES (5, 'Дони', 'female', 290.00, 'Шайр', 6, 'true', 42.00, 52, 'Джон');


create table comments
(
    id       bigint auto_increment
        primary key,
    comment  varchar(300) null,
    users_id bigint       not null,
    constraint comments_users__fk
        foreign key (users_id) references users (id)
);

create table races
(
    id         bigint auto_increment
        primary key,
    time       timestamp   not null,
    hippodrome varchar(20) null
);

INSERT INTO horse_racing_db.races (id, time, hippodrome) VALUES (23, '2021-10-21 22:44:00', 'Кировский');
INSERT INTO horse_racing_db.races (id, time, hippodrome) VALUES (24, '2021-10-06 18:53:00', 'Московский');
INSERT INTO horse_racing_db.races (id, time, hippodrome) VALUES (25, '2021-10-23 11:21:00', 'Минский');
INSERT INTO horse_racing_db.races (id, time, hippodrome) VALUES (26, '2021-10-22 11:30:00', 'Краснодарский');
INSERT INTO horse_racing_db.races (id, time, hippodrome) VALUES (27, '2021-10-28 18:00:00', 'Брестский ');

create table ratio
(
    race_id  bigint        not null,
    horse_id bigint        not null,
    type_id  int           not null,
    ratio    decimal(8, 3) null,
    primary key (race_id, horse_id, type_id),
    constraint ratio_bet_types__fk
        foreign key (type_id) references bets_type (id),
    constraint ratio_horses__fk
        foreign key (horse_id) references horses (id),
    constraint ratio_races__fk
        foreign key (race_id) references races (id)
);

create table bets
(
    id              bigint auto_increment
        primary key,
    amount_bet      decimal(10, 2)                      not null,
    ratio           decimal(10, 2)                      not null,
    races_id        bigint                              not null,
    transfer_status varchar(20)                         null,
    time            timestamp                           null,
    user_id         bigint      default 0               not null,
    horse_id        bigint                              not null,
    bets_type_id    int                                 not null,
    total_result    varchar(20) default 'not_processed' null,
    constraint bets_bet_types__fk
        foreign key (bets_type_id) references bets_type (id),
    constraint bets_horses__fk
        foreign key (horse_id) references horses (id),
    constraint bets_races__fk
        foreign key (races_id) references races (id),
    constraint bets_users__fk
        foreign key (user_id) references users (id)
);

create index horse_id
    on bets (horse_id);

create index user_id
    on bets (user_id);
    
    

INSERT INTO horse_racing_db.bets (id, amount_bet, ratio, races_id, transfer_status, time, user_id, horse_id, bets_type_id, total_result) VALUES (14, 200.00, 2.20, 23, null, '2021-10-20 18:43:09', 24, 1, 2, 'lose');
INSERT INTO horse_racing_db.bets (id, amount_bet, ratio, races_id, transfer_status, time, user_id, horse_id, bets_type_id, total_result) VALUES (15, 100.00, 2.00, 25, null, '2021-10-21 11:24:18', 24, 2, 1, 'win');
INSERT INTO horse_racing_db.bets (id, amount_bet, ratio, races_id, transfer_status, time, user_id, horse_id, bets_type_id, total_result) VALUES (16, 100.00, 2.00, 25, null, '2021-10-21 11:27:38', 24, 4, 1, 'win');
INSERT INTO horse_racing_db.bets (id, amount_bet, ratio, races_id, transfer_status, time, user_id, horse_id, bets_type_id, total_result) VALUES (17, 100.00, 1.10, 26, null, '2021-10-21 11:33:16', 24, 2, 2, 'win');
INSERT INTO horse_racing_db.bets (id, amount_bet, ratio, races_id, transfer_status, time, user_id, horse_id, bets_type_id, total_result) VALUES (18, 200.00, 1.50, 26, null, '2021-10-21 14:55:40', 24, 2, 1, 'not_processed');
INSERT INTO horse_racing_db.bets (id, amount_bet, ratio, races_id, transfer_status, time, user_id, horse_id, bets_type_id, total_result) VALUES (19, 200.00, 1.60, 27, null, '2021-10-26 14:47:36', 30, 3, 1, 'not_processed');


create table bets_type
(
    id   int auto_increment
        primary key,
    name varchar(40) null
);

INSERT INTO horse_racing_db.bets_type (id, name) VALUES (1, 'win');
INSERT INTO horse_racing_db.bets_type (id, name) VALUES (2, 'show');

create table races_has_horses
(
    horses_id bigint   not null,
    races_id  bigint   not null,
    place     smallint null,
    primary key (horses_id, races_id),
    constraint races_has_horses_horses__fk
        foreign key (horses_id) references horses (id),
    constraint races_has_horses_races__fk
        foreign key (races_id) references races (id)
);

INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (23, 1, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (23, 1, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (23, 2, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (23, 2, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (23, 3, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (23, 3, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (23, 4, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (23, 4, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 1, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 1, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 2, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 2, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 3, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 3, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 4, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 4, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 5, 1, 2.000);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (25, 5, 2, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 1, 1, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 1, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 2, 1, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 2, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 3, 1, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 3, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 4, 1, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 4, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 5, 1, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (26, 5, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 1, 1, 1.500);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 1, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 2, 1, 1.600);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 2, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 3, 1, 1.600);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 3, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 4, 1, 1.600);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 4, 2, 1.100);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 5, 1, 1.600);
INSERT INTO horse_racing_db.ratio (race_id, horse_id, type_id, ratio) VALUES (27, 5, 2, 1.100);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (1, 23, 4);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (1, 25, 4);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (1, 26, 1);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (1, 27, null);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (2, 23, 3);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (2, 24, null);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (2, 25, 1);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (2, 26, 2);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (2, 27, null);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (3, 23, 2);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (3, 24, null);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (3, 25, 2);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (3, 26, 3);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (3, 27, null);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (4, 23, 1);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (4, 24, null);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (4, 25, 3);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (4, 26, 4);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (4, 27, null);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (5, 24, null);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (5, 25, 5);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (5, 26, 5);
INSERT INTO horse_racing_db.races_has_horses (horses_id, races_id, place) VALUES (5, 27, null);
INSERT INTO horse_racing_db.comments (id, comment, users_id) VALUES (1, 'Все хорошо', 21);
INSERT INTO horse_racing_db.comments (id, comment, users_id) VALUES (2, 'Скачки отличные', 21);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (21, 'Станислав', 'stanislav.milavitsky123@gmail.com', null, '$2a$10$IiTn7cBkMJ70qXJryKAPFu4OSn0orfnc9kVs.Vj9il8PA0jtLiza.', '1', 0, null, '2021-10-15', 0, 'Милавицкий', 1, 0.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (22, 'Андрей', 'andrey.zubik@gmail.com', null, '$2a$10$hvIowsWPWQeQCLPZ1yX5LuWuvQm2CDqNmjtFFt3bWftiwEILqCV/q', '1', 0, null, '2021-10-15', 0, 'Зубик', 2, 0.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (23, 'Алексей', 'alex.alex@mail.ru', null, '$2a$10$hpEC5qDDP1c8ICQnWNh4fuUvvRRKSnvtSomVrJSpVnZiEz8/hiRZe', '0', 0, null, '2021-10-15', 0, 'Алексеев', 3, 0.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (24, 'Дмитрий', 'dmitr.losev@mail.ru', null, '$2a$10$Rig5lbfB.kErIJ1rHOmtx.Tp.qy3R8gbD4NeXy05Dsc6yrgUrh/GO', '1', 0, null, '2021-10-17', 0, 'Лосев', 2, 2435.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (25, 'Владимир', 'vladim.lishkevich@gmail.com', null, '$2a$10$VRYPrvtv5AuUbRy65PMRM.vQpF.r1RaCL4nNtiZBOpVmlIy4gxKxG', '1', 0, null, '2021-10-17', 0, 'Лишкевич', 3, 0.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (26, 'Юрий', 'uriy.karpuk@gmail.com', null, '$2a$10$197Keko603vA3tcy4Kw73uk/s6h22Ce9bjszBTmqPbT3jbVRaA5Yi', '1', 0, null, '2021-10-17', 0, 'Карпюк', 3, 0.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (27, 'Владислав', 'vlad.kozyr@gmail.com', null, '$2a$10$JpTSapA8We96g.WytuSCmOAhvr.i.Vvya5uyxnF1hiQnzioyJERz6', '1', 0, null, '2021-10-19', 0, 'Козырев', 2, 125.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (28, 'Александр', 'alex.getmut@gmail.com', null, '$2a$10$b.JsFjmwJhTnp5OVoWLYwOPOAJBE2aSq6D4gWO8h4.q36tzjC0w7K', '0', 0, null, '2021-10-19', 0, 'Гетмут', 2, 0.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (29, 'Александр', 'mialik.sashka@mail.ru', null, '$2a$10$PlVxJ1kw2k.xW53FTXXNlOxVwBc0PyZnAZYRk1h.dPWE2k9n4ZWV6', '1', 0, null, '2021-10-26', 0, 'Мялик', 2, 0.00);
INSERT INTO horse_racing_db.users (id, name, email, bank_account, password, status, persentage_of_win, avatar, date_of_register, rates, surname, role_id, cash) VALUES (30, 'Николай', 'kolya.kriv@gmail.com', null, '$2a$10$tb71QDZV.MBSp7hNCg.KHulo3R2To.s6Q.Yhh6dnVKtMmWNwowz5e', '1', 0, null, '2021-10-26', 0, 'Кривецкий', 2, 350.00);