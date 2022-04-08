package ru.yajaneya.Spring2Geekbrains.recom.services;

import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.recoms.RecomProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.RecomProduct;

import java.util.List;
import java.util.Optional;

@Service
public interface RecomService {
    List<RecomProductDto> findAll ();
    Optional<RecomProduct> findByID (Long id);
    void save (List<RecomProductDto> recomProductDtos);

}
