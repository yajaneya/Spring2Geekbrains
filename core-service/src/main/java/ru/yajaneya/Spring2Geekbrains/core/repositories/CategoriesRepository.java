package ru.yajaneya.Spring2Geekbrains.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yajaneya.Spring2Geekbrains.core.entities.Category;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository <Category, Long> {
    Optional<Category> findByCategoryName(String name);
}
