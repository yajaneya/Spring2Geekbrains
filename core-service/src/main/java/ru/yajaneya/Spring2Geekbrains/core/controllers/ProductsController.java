package ru.yajaneya.Spring2Geekbrains.core.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.core.converters.ProductConverter;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;
import ru.yajaneya.Spring2Geekbrains.core.services.CategoriesService;
import ru.yajaneya.Spring2Geekbrains.core.services.ProductsService;
import ru.yajaneya.Spring2Geekbrains.core.validators.ProductValidator;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;
    private final CategoriesService categoriesService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping
    public Page<ProductDto> getProducts(
    @RequestParam(name = "p", defaultValue = "1") Integer page,
    @RequestParam(name = "min_price", required = false) Integer minPrice,
    @RequestParam(name = "max_price", required = false) Integer maxPrice,
    @RequestParam(name = "categoryName", required = false) String categoryName,
    @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }

        Page<ProductDto> out = productsService.findAll(minPrice, maxPrice, titlePart,
                categoryName, page).
                    map(p -> productConverter.entityToDto(p)
        );

        System.out.println(out);

        return out;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById (@PathVariable Long id) {
        Product product = productsService.findByID(id)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт с id = " + id + " не найден."));
        return productConverter.entityToDto(product);
    }

    @PostMapping
    public ProductDto saveNewProduct (@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product.setId(null);
        product = productsService.save(product);
        return productConverter.entityToDto(product);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productsService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void delProduct (@PathVariable Long id) {
        productsService.deleteById(id);
    }

}
