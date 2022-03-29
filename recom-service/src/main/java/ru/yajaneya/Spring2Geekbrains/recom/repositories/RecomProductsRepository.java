package ru.yajaneya.Spring2Geekbrains.recom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yajaneya.Spring2Geekbrains.recom.entities.RecomProduct;

import java.util.Date;
import java.util.List;

@Repository
public interface RecomProductsRepository extends JpaRepository <RecomProduct, Long> {
    @Query("select b.productName, sum(b.productQuantity) from RecomProduct b where b.productDate > ?1 and b.typeRecom = ?2 group by b.productName")
    List<String> findFive(Date date, String type);
}
