package ru.yajaneya.Spring2Geekbrains.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDto;
import ru.yajaneya.Spring2Geekbrains.core.entities.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public Order dtoToEntity(OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setAddress(order.getAddress());
        out.setAddressToFront(order.getAddress().replace(" // ", ", "));
        out.setPhone(order.getPhone());
        out.setStatus(order.getStatus());
        out.setTotalPrice(order.getTotalPrice());
        out.setUsername(order.getUsername());
        out.setItems(order.getItems().stream().map(
                orderItemConverter::entityToDto).collect(Collectors.toList()));
        return out;
    }
}
