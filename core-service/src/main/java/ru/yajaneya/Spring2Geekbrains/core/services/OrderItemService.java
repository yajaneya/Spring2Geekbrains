package ru.yajaneya.Spring2Geekbrains.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.core.entities.Order;
import ru.yajaneya.Spring2Geekbrains.core.entities.OrderItem;
import ru.yajaneya.Spring2Geekbrains.core.repositories.OrderItemRepository;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    private Map<Long, OrderItem> cache;

    @PostConstruct
    private void init() {
        cache = new HashMap<>();
    }

    public List<OrderItem> findAll () {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> findByID (Long id) {
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        } else {
            Optional<OrderItem> orderItem = orderItemRepository.findById(id);
            cache.put(id, orderItem.get());
            return orderItem;
        }
    }

    public OrderItem save (OrderItem orderItem) {
        cache.put(orderItem.getId(), orderItem);
        return orderItemRepository.save(orderItem);
    }

}
