package ru.yajaneya.Spring2Geekbrains.recom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.recoms.BuyProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.BuyProduct;
import ru.yajaneya.Spring2Geekbrains.recom.repositories.BuyProductsRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuyProductsService {
    private final BuyProductsRepository buyProductsRepository;

    public List<BuyProductDto> findAll () {

        List<BuyProductDto> list = new ArrayList<>();
        List<BuyProductDto> list1 = new ArrayList<>();

        buyProductsRepository.findFive().forEach(s -> {
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
            for (int i = 5; i <list1.size(); i++) {
                list1.remove(i);
            }

        }

        return list1;
    }

    public Optional<BuyProduct> findByID (Long id) {
        return buyProductsRepository.findById(id);
    }

    public BuyProduct save (BuyProduct buyProduct) {
        return buyProductsRepository.save(buyProduct);
    }

}
