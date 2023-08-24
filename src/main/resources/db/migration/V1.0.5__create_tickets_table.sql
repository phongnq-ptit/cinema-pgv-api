create table tickets
(
    id                  bigint                             not null auto_increment,
    uuid                binary(16)                         not null,
    purchase_uuid       binary(16)                         not null,
    file_uuid           binary(16)                         not null,
    primary key (id)
);