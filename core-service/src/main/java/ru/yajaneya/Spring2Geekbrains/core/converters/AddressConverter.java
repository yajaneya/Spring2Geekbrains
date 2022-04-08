package ru.yajaneya.Spring2Geekbrains.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDetailsDto;

@Component
@RequiredArgsConstructor
public class AddressConverter {

    public String dtoToString(OrderDetailsDto orderDetailsDto) {
        String address = orderDetailsDto.getCountryCode() +
                " // " +
                orderDetailsDto.getPostalCode() +
                " // " +
                orderDetailsDto.getAdminArea1() +
                " // " +
                orderDetailsDto.getAdminArea2() +
                " // " +
                orderDetailsDto.getAddressLine1() +
                " // " +
                orderDetailsDto.getAddressLine2();
        return address;
    }

    public OrderDetailsDto StringToDto(String address) {
        OrderDetailsDto out = new OrderDetailsDto();
        String [] addr =  address.split(" // ");
        out.setCountryCode(addr[0]);
        out.setPostalCode(addr[1]);
        out.setAdminArea1(addr[2]);
        out.setAdminArea2(addr[3]);
        out.setAddressLine1(addr[4]);
        out.setAddressLine2(addr[5]);
        return out;
    }
}
