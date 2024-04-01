create table catalogs
(
    id           varchar(255) not null,
    display_name varchar(255),
    primary key (id)
);

create table products
(
    id          uuid not null,
    description varchar(255),
    image_alt   varchar(255),
    image_src   varchar(255),
    name        varchar(255),
    price       varchar(255),
    catalog_id  varchar(255),
    quantity    integer,
    primary key (id)
);
alter table products
    add constraint FKr9g72vsfwc9lupwutnut4w7kf foreign key (catalog_id) references catalogs;