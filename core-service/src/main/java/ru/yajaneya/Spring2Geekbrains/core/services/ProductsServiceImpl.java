package ru.yajaneya.Spring2Geekbrains.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;
import ru.yajaneya.Spring2Geekbrains.core.repositories.ProductsRepository;
import ru.yajaneya.Spring2Geekbrains.core.repositories.specifications.ProductsSpecifications;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService{

    private final ProductsRepository productsRepository;

    private final int AMOUNT_LINES_IN_PAGE = 8; // количество строк, отображаемых на странице

    public Page<Product> findAll(
            Integer minPrice,
            Integer maxPrice,
            String partTitle,
            String categoryName,
            Integer page) {
        System.out.println("Реализация");

        Specification<Product> spec = Specification.where(null);

        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ProductsSpecifications.titleLike(partTitle));
        }
        if (categoryName != null) {
            if (!categoryName.equals("")) {
                spec = spec.and(ProductsSpecifications.categoryEqual(categoryName));
            }
        }

        return productsRepository.findAll(spec, PageRequest.of(page - 1, AMOUNT_LINES_IN_PAGE));

    }

    public Optional<Product> findByID (Long id) {
        return productsRepository.findById(id);
    }

    public Product save (Product product) {
        return productsRepository.save(product);
    }

    @Transactional
    public Product update (ProductDto productDto) {
        Long id = productDto.getId();
        Product product = findByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить. Продукт с id = " + id + " не найден."));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        return product;
    }


    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

}
