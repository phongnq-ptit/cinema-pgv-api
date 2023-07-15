create table users
(
    id         bigint                             not null auto_increment,
    uuid       binary(16)                         not null,
    role       varchar(100)                       not null,
    user_name  varchar(100)                       not null,
    email      varchar(100)                       not null,
    password   varchar(100)                       not null,
    address    varchar(100)                       not null,
    cinema_id  binary(16)                         null,
    active     int      default 0                 not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null,
    primary key (id)
);