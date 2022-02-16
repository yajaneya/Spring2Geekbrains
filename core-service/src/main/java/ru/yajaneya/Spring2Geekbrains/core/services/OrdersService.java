package ru.yajaneya.Spring2Geekbrains.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yajaneya.Spring2Geekbrains.api.carts.CartDto;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDetailsDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.api.recoms.BuyProductDto;
import ru.yajaneya.Spring2Geekbrains.core.entities.Order;
import ru.yajaneya.Spring2Geekbrains.core.entities.OrderItem;
import ru.yajaneya.Spring2Geekbrains.core.integretions.CartServiceIntegration;
import ru.yajaneya.Spring2Geekbrains.core.integretions.RecomServiceIntegration;
import ru.yajaneya.Spring2Geekbrains.core.repositories.OrdersRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {


    private final OrdersRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductsService productsService;
    private final RecomServiceIntegration recomServiceIntegration;

    @Value("${constant.recom-send}")
    private int timePause;
    private List<BuyProductDto> buyProductDtos = new ArrayList<>();
    private Date saveDate = new Date();



    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    BuyProductDto buyProductDto = new BuyProductDto();
                    buyProductDto.setProductId(o.getProductId());
                    buyProductDto.setProductName(o.getProductTitle());
                    buyProductDto.setProductQuantity(o.getQuantity());
                    buyProductDtos.add(buyProductDto);
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findByID(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        ordersRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
        Date date = new Date();
        if ((date.getTime() - saveDate.getTime()) > timePause) {
            saveDate = date;
            //отправка данных в реком-сервис
            recomServiceIntegration.sendProductRecom(buyProductDtos);
            buyProductDtos.clear();
        }
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }
}
