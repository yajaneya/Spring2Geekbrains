package ru.yajaneya.Spring2Geekbrains.recom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.recoms.RecomProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.RecomProduct;
import ru.yajaneya.Spring2Geekbrains.recom.repositories.RecomProductsRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BuyProductsService implements RecomService{
    private final RecomProductsRepository recomProductsRepository;

    @Value("${constant.time-products-recom}")
    private long timeProductRecom;

    public List<RecomProductDto> findAll () {

        List<RecomProductDto> list = new ArrayList<>();
        List<RecomProductDto> list1 = new ArrayList<>();

        Date nowDate = new Date();
        long n = nowDate.getTime() - timeProductRecom;
        Date date = new Date(n);

        recomProductsRepository.findFive(date, "core").forEach(s -> {
            String [] sm = s.split(",");
            RecomProductDto recomProductDto = new RecomProductDto();
            recomProductDto.setProductName(sm[0]);
            recomProductDto.setProductQuantity(Integer.parseUnsignedInt(sm[1]));
            list.add(recomProductDto);
        });

        list.stream().sorted(Comparator.comparing(RecomProductDto::getProductQuantity).reversed()).forEach(l -> {
            list1.add(l);
        });


        if (list1.size() > 5) {
            list.clear();
            for (int i = 0; i <5; i++) {
                list.add(list1.get(i));
            }
        } else {
            return list1;
        }
        return list;
    }

    public Optional<RecomProduct> findByID (Long id) {
        return recomProductsRepository.findById(id);
    }

    public void save (List<RecomProductDto> recomProductDtos) {
        recomProductDtos.forEach(b -> {
            RecomProduct recomProduct = new RecomProduct();
            recomProduct.setProductId(b.getProductId());
            recomProduct.setProductName(b.getProductName());
            recomProduct.setProductQuantity(b.getProductQuantity());
            recomProduct.setProductDate(new Date());
            recomProduct.setTypeRecom("core");
            recomProductsRepository.save(recomProduct);
        });
    }

}
