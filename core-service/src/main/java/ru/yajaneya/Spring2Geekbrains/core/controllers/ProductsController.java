package ru.yajaneya.Spring2Geekbrains.core.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.AppError;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;
import ru.yajaneya.Spring2Geekbrains.core.mappers.ProductMapper;
import ru.yajaneya.Spring2Geekbrains.core.services.ProductsService;
import ru.yajaneya.Spring2Geekbrains.core.validators.ProductValidator;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name="Продукты", description = "Методы работы с продуктами")
public class ProductsController {

    private final ProductsService productsService;
    private final ProductValidator productValidator;
    private final ProductMapper mapper;

    @Operation(
            summary = "Запрос на получение страницы продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )

    @GetMapping
    public Page<ProductDto> getProducts(
    @RequestParam(name = "p", defaultValue = "1") Integer page,
    @RequestParam(name = "min_price", required = false) Integer minPrice,
    @RequestParam(name = "max_price", required = false) Integer maxPrice,
    @RequestParam(name = "categoryName", required = false) String categoryName,
    @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }

        Page<ProductDto> out = productsService.findAll(minPrice, maxPrice, titlePart,
                categoryName, page).
                    map(p -> mapper.mapDto(p)
        );

        return out;
    }

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Неуспешный ответ", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )

            }
    )
    @GetMapping("/{id}")
    public ProductDto getProductById (
            @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id
    ) {
        Product product = productsService.findByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт с id = " + id + " не найден."));
        return mapper.mapDto(product);
    }

    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @PostMapping
    public ProductDto saveNewProduct (@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = mapper.mapView(productDto);
        product.setId(null);
        product = productsService.save(product);
        return mapper.mapDto(product);
    }

    @Operation(
            summary = "Запрос на обновление продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productsService.update(productDto);
        return mapper.mapDto(product);
    }

    @Operation(
            summary = "Запрос на удаление продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void delProduct (@PathVariable Long id) {
        productsService.deleteById(id);
    }

    @GetMapping("/cache")
    public List<String> getCache () {
        return productsService.getCache();
    }

}
