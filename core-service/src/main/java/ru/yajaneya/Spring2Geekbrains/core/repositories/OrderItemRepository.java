package ru.yajaneya.Spring2Geekbrains.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yajaneya.Spring2Geekbrains.core.entities.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
