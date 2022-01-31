package ru.yajaneya.Spring2Geekbrains.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;

@Repository
public interface ProductsRepository extends JpaRepository <Product, Long>, JpaSpecificationExecutor<Product> {
}
