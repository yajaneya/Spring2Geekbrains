package ru.yajaneya.Spring2Geekbrains.core.mappers;


import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDto;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderItemDto;
import ru.yajaneya.Spring2Geekbrains.core.entities.Order;
import ru.yajaneya.Spring2Geekbrains.core.entities.OrderItem;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring"
)

public abstract class OrderMapper {

    public  abstract Order mapView(OrderDto orderDto);

    public  abstract  OrderDto mapDto(Order order);

    @AfterMapping
    public void afterMapView(OrderDto orderDto, @MappingTarget Order order) {
        List<OrderItem> items = new ArrayList<>();
        orderDto.getItems().forEach(i -> {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            product.setId(i.getProductId());
            product.setTitle(i.getProductTitle());
            orderItem.setProduct(product);
            orderItem.setQuantity(i.getQuantity());
            orderItem.setPricePerProduct(i.getPricePerProduct());
            orderItem.setPrice(i.getPrice());
            items.add(orderItem);
        });
        order.setItems(items);
    }

    @AfterMapping
    public void afterMapDto(Order order, @MappingTarget OrderDto orderDto) {
        List<OrderItemDto> itemDtos = new ArrayList<>();
        order.getItems().forEach(i -> {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setProductId(i.getProduct().getId());
            orderItemDto.setProductTitle(i.getProduct().getTitle());
            orderItemDto.setQuantity(i.getQuantity());
            orderItemDto.setPricePerProduct(i.getPricePerProduct());
            orderItemDto.setPrice(i.getPrice());
            itemDtos.add(orderItemDto);
        });
        orderDto.setItems(itemDtos);
    }

}
