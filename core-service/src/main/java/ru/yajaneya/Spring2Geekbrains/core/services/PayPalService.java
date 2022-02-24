package ru.yajaneya.Spring2Geekbrains.core.services;

import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {
    private final OrdersService ordersService;

    @Transactional
    public OrderRequest createOrderRequest(Long orderId) {
        ru.yajaneya.Spring2Geekbrains.core.entities.Order order = ordersService.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Spring Web Market")
                .landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        List<Item> items = order.getItems().stream()
                .map(orderItem -> new Item()
                        .name(orderItem.getProduct().getTitle())
                        .unitAmount(new Money().currencyCode("RUB").value(String.valueOf(orderItem.getPricePerProduct())))
                        .quantity(String.valueOf(orderItem.getQuantity())))
                .collect(Collectors.toList());

        AmountWithBreakdown awb = new AmountWithBreakdown().currencyCode("RUB").value(String.valueOf(order.getTotalPrice()))
                .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("RUB").value(String.valueOf(order.getTotalPrice()))));

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .description("Spring Web Market Order")
                .amountWithBreakdown(awb)
                .items(items)
                .shippingDetail(new ShippingDetail().name(new Name().fullName(order.getUsername()))
                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
                                .adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }
}
