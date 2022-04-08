create table recomproducts (
    id                  bigserial primary key,
    product_id          bigint not null,
    product_name        varchar(255),
    product_date        date,
    product_quantity    int,
    type_recom          varchar(50),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

