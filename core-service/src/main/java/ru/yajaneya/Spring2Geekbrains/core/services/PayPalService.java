package ru.yajaneya.Spring2Geekbrains.core.services;

import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDetailsDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.core.converters.AddressConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {
    private final OrdersService ordersService;
    private final AddressConverter addressConverter;

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

        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown().currencyCode("RUB").value(String.valueOf(order.getTotalPrice()))
                .amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("RUB").value(String.valueOf(order.getTotalPrice()))));

        List<Item> items = order.getItems().stream()
                .map(orderItem -> new Item()
                        .name(orderItem.getProduct().getTitle())
                        .unitAmount(new Money().currencyCode("RUB").value(String.valueOf(orderItem.getPricePerProduct())))
                        .quantity(String.valueOf(orderItem.getQuantity())))
                .collect(Collectors.toList());

        OrderDetailsDto address = addressConverter.StringToDto(order.getAddress());

        ShippingDetail shippingDetail = new ShippingDetail().name(new Name().fullName(order.getUsername()))
                .addressPortable(new AddressPortable()
                        .addressLine1(address.getAddressLine1())
                        .addressLine2(address.getAddressLine2())
                        .adminArea2(address.getAdminArea2())
                        .adminArea1(address.getAdminArea1())
                        .postalCode(address.getPostalCode())
                        .countryCode(address.getCountryCode()));

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .description("Spring Web Market Order")
                .amountWithBreakdown(amountWithBreakdown)
                .items(items)
                .shippingDetail(shippingDetail);
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }
}
