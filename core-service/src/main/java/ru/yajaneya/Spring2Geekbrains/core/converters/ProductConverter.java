package ru.yajaneya.Spring2Geekbrains.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;
import ru.yajaneya.Spring2Geekbrains.core.services.CategoriesService;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoriesService categoriesService;

    public Product dtoToEntity (ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getTitle(),
                productDto.getPrice(),
                categoriesService.findByCategoryName(productDto.getCategory())
                        .orElseThrow(() -> new ResourceNotFoundException
                                ("Категория " + productDto.getCategory() + " не найдена.")));
    }

    public ProductDto entityToDto (Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getCategory().getCategoryName());
    }

}
