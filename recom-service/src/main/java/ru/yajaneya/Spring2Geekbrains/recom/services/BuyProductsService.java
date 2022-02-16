package ru.yajaneya.Spring2Geekbrains.recom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.recoms.BuyProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.BuyProduct;
import ru.yajaneya.Spring2Geekbrains.recom.repositories.BuyProductsRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BuyProductsService {
    private final BuyProductsRepository buyProductsRepository;

    @Value("${constant.time-products-recom}")
    private long timeProductRecom;

    public List<BuyProductDto> findAll () {

        List<BuyProductDto> list = new ArrayList<>();
        List<BuyProductDto> list1 = new ArrayList<>();

        Date nowDate = new Date();
        long n = nowDate.getTime() - timeProductRecom;
        Date date = new Date(n);

        buyProductsRepository.findFive(date).forEach(s -> {
            String [] sm = s.split(",");
            BuyProductDto buyProductDto = new BuyProductDto();
            buyProductDto.setProductName(sm[0]);
            buyProductDto.setProductQuantity(Integer.parseUnsignedInt(sm[1]));
            list.add(buyProductDto);
        });

        list.stream().sorted(Comparator.comparing(BuyProductDto::getProductQuantity).reversed()).forEach(l -> {
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

    public Optional<BuyProduct> findByID (Long id) {
        return buyProductsRepository.findById(id);
    }

    public void save (List<BuyProductDto> buyProductDtos) {
        buyProductDtos.forEach(b -> {
            BuyProduct buyProduct = new BuyProduct();
            buyProduct.setProductId(b.getProductId());
            buyProduct.setProductName(b.getProductName());
            buyProduct.setProductQuantity(b.getProductQuantity());
            buyProduct.setProductDate(new Date());
            buyProductsRepository.save(buyProduct);
        });
    }

}
