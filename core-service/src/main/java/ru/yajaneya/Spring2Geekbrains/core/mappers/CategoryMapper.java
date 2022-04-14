package ru.yajaneya.Spring2Geekbrains.core.mappers;


import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.yajaneya.Spring2Geekbrains.api.core.CategoryDto;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderDto;
import ru.yajaneya.Spring2Geekbrains.api.core.OrderItemDto;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.core.entities.Category;
import ru.yajaneya.Spring2Geekbrains.core.entities.Order;
import ru.yajaneya.Spring2Geekbrains.core.entities.OrderItem;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring"
)

public abstract class CategoryMapper {

    public  abstract Category mapView(CategoryDto categoryDto);

    public  abstract  CategoryDto mapDto(Category category);

    @AfterMapping
    public void afterMapView(CategoryDto categoryDto, @MappingTarget Category category) {
        List<Product> products = new ArrayList<>();
        categoryDto.getProductDtos().forEach(p -> {
            Product product = new Product();
            product.setId(p.getId());
            product.setTitle(p.getTitle());
            product.setCategory(category);
            product.setPrice(p.getPrice());
            products.add(product);
        });
        category.setProducts(products);
    }

    @AfterMapping
    public void afterMapDto(Category category, @MappingTarget CategoryDto categoryDto) {
        List<ProductDto> productDtos = new ArrayList<>();
        category.getProducts().forEach(p -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(p.getId());
            productDto.setTitle(p.getTitle());
//            productDto.setCategoryId(category.getId());
//            productDto.setCategoryName(category.getCategoryName());
            productDto.setCategoryDto(categoryDto);
            productDto.setPrice(p.getPrice());
            productDtos.add(productDto);
        });
        categoryDto.setProductDtos(productDtos);
    }

}
