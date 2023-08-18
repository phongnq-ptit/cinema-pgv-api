create table movie_public
(
    id            bigint                             not null auto_increment,
    uuid          binary(16)                         not null,
    movie_uuid    binary(16)                         not null,
    branch_uuid   binary(16)                         not null,
    start_date    datetime                           not null,
    end_date      datetime                           not null,
    price         int                                not null,
    total_tickets int                                not null,
    primary key (id)
);
