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

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    private Map<Long, Product> cache;

    @PostConstruct
    private void init() {
        cache = new HashMap<>();
    }

    public Page<Product> findAll(
            Integer minPrice,
            Integer maxPrice,
            String partTitle,
            String categoryName,
            Integer page) {

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

        return productsRepository.findAll(spec, PageRequest.of(page - 1, 8));
    }

    public Optional<Product> findByID (Long id) {
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        }{
            Optional<Product> product = productsRepository.findById(id);
            cache.put(id, product.get());
            return product;
        }
    }

    public Product save (Product product) {
        cache.put(product.getId(), product);
        return productsRepository.save(product);
    }

    @Transactional
    public Product update (ProductDto productDto) {
        Long id = productDto.getId();
        Product product = findByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить. Продукт с id = " + id + " не найден."));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        if (cache.containsKey(id)) {
            cache.put(id, product);
        }
        return product;
    }


    public void deleteById(Long id) {
        if (cache.containsKey(id)) {
            cache.remove(id);
        }
        productsRepository.deleteById(id);
    }

   public List<String> getCache () {
        List<String> products = new ArrayList<>();
        cache.forEach((k, v) -> {
            String product = v.getId() + " -> " + v.getTitle();
            products.add(product);
        });
        return products;
    }

}
