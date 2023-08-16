create table files
(
    id         bigint                             not null auto_increment,
    uuid       binary(16)                         not null,
    file_name  varchar(200)                       not null,
    url        text                               not null,
    size       int                                null,
    type       varchar(200)                       not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null,
    primary key (id)
);

create table categories
(
    id           bigint                             not null auto_increment,
    uuid         binary(16)                         not null,
    name         varchar(200)                       not null,
    description  longtext                           not null,
    created_at   datetime default CURRENT_TIMESTAMP not null,
    updated_at   datetime default CURRENT_TIMESTAMP not null,
    primary key (id)
);

create table movies
(
    id           bigint                             not null auto_increment,
    uuid         binary(16)                         not null,
    name         varchar(200)                       not null,
    duration     int                                not null,
    author       varchar(200)                       not null,
    release_date datetime                           null,
    active       int      default 1                 not null,
    created_at   datetime default CURRENT_TIMESTAMP not null,
    updated_at   datetime default CURRENT_TIMESTAMP not null,
    primary key (id)
);

create table movie_category
(
    id            bigint                             not null auto_increment,
    uuid          binary(16)                         not null,
    movie_uuid    binary(16)                         not null,
    category_uuid binary(16)                         not null,
    primary key (id)
);

create table movie_file
(
    id            bigint                             not null auto_increment,
    uuid          binary(16)                         not null,
    movie_uuid    binary(16)                         not null,
    file_uuid     binary(16)                         not null,
    primary key (id)
);

create table movie_public
(
    id            bigint                             not null auto_increment,
    uuid          binary(16)                         not null,
    movie_uuid    binary(16)                         not null,
    branch_uuid   binary(16)                         not null,
    start_date    datetime                           not null,
    end_date      datetime                           not null,
    total_tickets int                                not null,
    primary key (id)
);

