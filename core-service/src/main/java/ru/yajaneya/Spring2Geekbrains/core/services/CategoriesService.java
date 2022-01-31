package ru.yajaneya.Spring2Geekbrains.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.core.entities.Category;
import ru.yajaneya.Spring2Geekbrains.core.repositories.CategoriesRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public Optional<Category> findByCategoryName (String name) {
        return categoriesRepository.findByCategoryName(name);
    }
}
