create table purchases
(
    id                  bigint                             not null auto_increment,
    uuid                binary(16)                         not null,
    user_uuid           binary(16)                         not null,
    movie_public_uuid   binary(16)                         not null,
    quantity_of_tickets int                                not null,
    created_at          datetime default CURRENT_TIMESTAMP not null,
    updated_at          datetime default CURRENT_TIMESTAMP not null,
    primary key (id)
);