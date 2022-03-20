package ru.yajaneya.Spring2Geekbrains.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDetailsDto;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.AppError;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.core.converters.OrderConverter;
import ru.yajaneya.Spring2Geekbrains.core.services.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name="Заказы", description = "Методы работы с заказами")
public class OrdersController {
    private final OrdersService ordersService;
    private final OrderConverter orderConverter;


    @Operation(
            summary = "Запрос на получение списка заказов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return ordersService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderDetailsDto.setCountryCode("RU"); //TODO сделать список Enum стран с кодами и на базе него построить заполнение поля
        ordersService.createOrder(username, orderDetailsDto);
    }

    @Operation(
            summary = "Запрос на получение заказа с указанным id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Неуспешный ответ", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        return orderConverter.entityToDto(ordersService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }

}
