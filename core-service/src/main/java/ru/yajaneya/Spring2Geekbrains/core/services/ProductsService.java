package ru.yajaneya.Spring2Geekbrains.core.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;

import java.util.Optional;

@Service
public interface ProductsService {
    Page<Product> findAll(Integer minPrice,
                                 Integer maxPrice,
                                 String partTitle,
                                 String categoryName,
                                 Integer page);
    Optional<Product> findByID (Long id);
    Product save (Product product);
    Product update (ProductDto productDto);
    void deleteById(Long id);

}
