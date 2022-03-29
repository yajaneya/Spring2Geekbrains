package ru.yajaneya.Spring2Geekbrains.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yajaneya.Spring2Geekbrains.api.carts.CartDto;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDetailsDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.api.recoms.RecomProductDto;
import ru.yajaneya.Spring2Geekbrains.core.converters.AddressConverter;
import ru.yajaneya.Spring2Geekbrains.core.entities.Order;
import ru.yajaneya.Spring2Geekbrains.core.entities.OrderItem;
import ru.yajaneya.Spring2Geekbrains.core.integretions.CartServiceIntegration;
import ru.yajaneya.Spring2Geekbrains.core.integretions.RecomServiceIntegration;
import ru.yajaneya.Spring2Geekbrains.core.repositories.OrdersRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {


    private final OrdersRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductsService productsService;
    private final RecomServiceIntegration recomServiceIntegration;
    private final AddressConverter addressConverter;

    @Value("${constant.recom-send}")
    private int timePause;
    private List<RecomProductDto> recomProductDtos = new ArrayList<>();
    private Date saveDate = new Date();



    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setAddress(addressConverter.dtoToString(orderDetailsDto));
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    RecomProductDto recomProductDto = new RecomProductDto();
                    recomProductDto.setProductId(o.getProductId());
                    recomProductDto.setProductName(o.getProductTitle());
                    recomProductDto.setProductQuantity(o.getQuantity());
                    recomProductDto.setType("core");
                    recomProductDtos.add(recomProductDto);
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findByID(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        order.setStatus(Order.Status.CREATED.name());
        ordersRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
        Date date = new Date();
        if ((date.getTime() - saveDate.getTime()) > timePause) {
            saveDate = date;
            //отправка данных в реком-сервис
            recomServiceIntegration.sendProductRecom(recomProductDtos);
            recomProductDtos.clear();
        }
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }
}
