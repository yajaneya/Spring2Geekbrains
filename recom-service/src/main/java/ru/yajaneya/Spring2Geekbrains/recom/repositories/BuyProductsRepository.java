package ru.yajaneya.Spring2Geekbrains.recom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yajaneya.Spring2Geekbrains.api.recoms.BuyProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.BuyProduct;

import java.util.List;

@Repository
public interface BuyProductsRepository extends JpaRepository <BuyProduct, Long> {
    @Query("select b.productName, sum(b.productQuantity) from BuyProduct b group by b.productName")
    List<String> findFive();
}
