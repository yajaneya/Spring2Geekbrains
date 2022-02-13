package ru.yajaneya.Spring2Geekbrains.recom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.recoms.PutToCartProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.PutToCartProduct;
import ru.yajaneya.Spring2Geekbrains.recom.repositories.PutToCartProductsRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PutToCartProductsService {
    private final PutToCartProductsRepository putToCartProductsRepository;

    public List<PutToCartProductDto> findAll () {

        List<PutToCartProductDto> list = new ArrayList<>();
        List<PutToCartProductDto> list1 = new ArrayList<>();

        Date nowDate = new Date();
        nowDate.setHours(0);
        nowDate.setMinutes(0);
        nowDate.setSeconds(0);
        Long n = nowDate.getTime() - 500l;
        Date date = new Date(n);

        putToCartProductsRepository.findFive(date).forEach(s -> {
            String [] sm = s.split(",");
            PutToCartProductDto putToCartProductDto = new PutToCartProductDto();
            putToCartProductDto.setProductName(sm[0]);
            putToCartProductDto.setProductQuantity(Integer.parseUnsignedInt(sm[1]));
            list.add(putToCartProductDto);
        });

        list.stream().sorted(Comparator.comparing(PutToCartProductDto::getProductQuantity).reversed()).forEach(l -> {
            list1.add(l);
        });

        if (list1.size() > 5) {
            for (int i = 5; i <list1.size(); i++) {
                list1.remove(i);
            }

        }

        return list1;
    }


    public Optional<PutToCartProduct> findByID (Long id) {
        return putToCartProductsRepository.findById(id);
    }

    public PutToCartProduct save (PutToCartProduct putToCartProduct) {
        return putToCartProductsRepository.save(putToCartProduct);
    }
}
