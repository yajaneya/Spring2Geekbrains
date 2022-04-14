package ru.yajaneya.Spring2Geekbrains.core.mappers;


import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderItemDto;
import ru.yajaneya.Spring2Geekbrains.core.entities.OrderItem;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring"
)

public abstract class OrderItemMapper {

    public  abstract OrderItem mapView(OrderItemDto orderItemDto);

    public  abstract  OrderItemDto mapDto(OrderItem orderItem);

    @AfterMapping
    public void afterMapView(OrderItemDto orderItemDto, @MappingTarget OrderItem orderItem) {
        Product product = new Product();
        product.setId(orderItemDto.getProductId());
        product.setTitle(orderItemDto.getProductTitle());
        orderItem.setProduct(product);
    }

    @AfterMapping
    public void afterMapDto(OrderItem orderItem, @MappingTarget OrderItemDto orderItemDto) {
        orderItemDto.setProductId(orderItem.getProduct().getId());
        orderItemDto.setProductTitle(orderItem.getProduct().getTitle());
    }

}
