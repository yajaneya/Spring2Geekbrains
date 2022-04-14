package ru.yajaneya.Spring2Geekbrains.core.mappers;


import org.mapstruct.*;
import ru.yajaneya.Spring2Geekbrains.api.core.CategoryDto;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.core.entities.Category;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;

@Mapper(
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring"
)

public abstract class ProductMapper {

    public  abstract Product mapView(ProductDto productDto);

    public  abstract  ProductDto mapDto(Product product);

    @AfterMapping
    public void afterMapView(ProductDto productDto, @MappingTarget Product product) {
        Category category = new Category();
        category.setId(productDto.getCategoryDto().getId());
        category.setCategoryName(productDto.getCategoryDto().getCategoryName());
        product.setCategory(category);
    }

    @AfterMapping
    public void afterMapDto(Product product, @MappingTarget ProductDto productDto) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setCategoryName(product.getCategory().getCategoryName());
        productDto.setCategoryDto(categoryDto);
    }

}
