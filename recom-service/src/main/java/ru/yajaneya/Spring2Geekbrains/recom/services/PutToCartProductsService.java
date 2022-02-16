package ru.yajaneya.Spring2Geekbrains.recom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.recoms.PutToCartProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.BuyProduct;
import ru.yajaneya.Spring2Geekbrains.recom.entities.PutToCartProduct;
import ru.yajaneya.Spring2Geekbrains.recom.repositories.PutToCartProductsRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PutToCartProductsService {
    private final PutToCartProductsRepository putToCartProductsRepository;

    @Value("${constant.time-cart-recom}")
    private long timeCartRecom;


    public List<PutToCartProductDto> findAll () {

        List<PutToCartProductDto> list = new ArrayList<>();
        List<PutToCartProductDto> list1 = new ArrayList<>();

        Date nowDate = new Date();
        long n = nowDate.getTime() - timeCartRecom;
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
            list.clear();
            for (int i = 0; i <5; i++) {
                list.add(list1.get(i));
            }
        } else {
            return list1;
        }
        return list;
    }


    public Optional<PutToCartProduct> findByID (Long id) {
        return putToCartProductsRepository.findById(id);
    }

    public void save (List<PutToCartProduct> putToCartProducts) {
        putToCartProducts.forEach(b -> {
            PutToCartProduct putToCartProduct = new PutToCartProduct();
            putToCartProduct.setProductId(b.getProductId());
            putToCartProduct.setProductName(b.getProductName());
            putToCartProduct.setProductQuantity(b.getProductQuantity());
            putToCartProduct.setProductDate(new Date());
            putToCartProductsRepository.save(putToCartProduct);
        });
    }
}
