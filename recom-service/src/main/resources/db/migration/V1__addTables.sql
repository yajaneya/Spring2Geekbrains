create table buyproducts (
    id                  bigserial primary key,
    product_id          bigint not null,
    product_name        varchar(255),
    product_date        date,
    product_quantity    int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

--insert into buyproducts (product_id, product_name, product_date, product_quantity)
--values
--(1, 'Milk', '2022-02-11', 5),
--(2, 'Bred', '2022-02-12', 5),
--(7, 'Banana', '2022-02-12', 5),
--(1, 'Milk', '2022-02-12', 5),
--(5, 'Tea', '2022-02-12', 5),
--(8, 'Orange', '2022-02-12', 5),
--(11, 'Cream', '2022-02-12', 5),
--(1, 'Milk', '2022-01-02', 5),
--(5, 'Tea', '2022-02-12', 5),
--(8, 'Orange', '2022-02-12', 5),
--(11, 'Cream', '2022-02-12', 5);
--
create table puttocartproducts (
    id                  bigserial primary key,
    product_id          bigint not null,
    product_name        varchar(255),
    product_date        date,
    product_quantity    int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

--insert into puttocartproducts (product_id, product_name, product_date, product_quantity)
--values
--(1, 'Milk', '2022-02-16', 5),
--(2, 'Bred', '2022-02-16', 5),
--(7, 'Banana', '2022-02-16', 5),
--(1, 'Milk', '2022-02-15', 5),
--(5, 'Tea', '2022-02-16', 5),
--(8, 'Orange', '2022-02-16', 5),
--(11, 'Cream', '2022-02-16', 5),
--(1, 'Milk', '2022-02-14', 5),
--(5, 'Tea', '2022-02-16', 5),
--(8, 'Orange', '2022-02-16', 5),
--(11, 'Cream', '2022-02-16', 5);
