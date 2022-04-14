package ru.yajaneya.Spring2Geekbrains.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.core.entities.Category;
import ru.yajaneya.Spring2Geekbrains.core.repositories.CategoriesRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    private Map<String, Category> cache;

    @PostConstruct
    private void init() {
        cache = new HashMap<>();
    }


    public Optional<Category> findByCategoryName (String name) {
        if (cache.containsKey(name)) {
            return Optional.of(cache.get(name));
        } else {
            Optional<Category> category = categoriesRepository.findByCategoryName(name);
            cache.put(name, category.get());
            return category;
        }
    }
}
