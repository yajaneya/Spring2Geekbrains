create table categories (
    id              bigserial primary key,
    category_name   varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into categories (category_name)
values
('Milk'),
('Bakery'),
('Fruits'),
('Vegetables'),
('Grocery'),
('Fish'),
('Meat');

create table if not exists products
(
    id bigserial primary key,
    category_id bigint not null references categories (id),
    title varchar(255),
    price numeric(8,2) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
    );

insert into products (category_id, title, price)
values
(1, 'Milk', 50.25),
(2, 'Bred', 30.10),
(5, 'Solt', 15.01),
(5, 'Soda', 12.00),
(5, 'Tea', 75.00),
(3, 'Apple', 55.00),
(3, 'Banana', 75.00),
(3, 'Orange', 100.00),
(4, 'Potato', 60.00),
(2, 'Cake', 95.00),
(1, 'Cream', 81.00),
(6, 'Salmon', 950.00),
(7, 'Beef', 450.00),
(7, 'Chiken', 180.00),
(2, 'Hamburger', 115.00),
(2, 'Pizza', 355.00),
(3, 'Grape', 105.00),
(1, 'Cheese', 515.00),
(1, 'Butter', 650.00),
(7, 'Steak', 555.00),
(3, 'Plum', 74.00);
