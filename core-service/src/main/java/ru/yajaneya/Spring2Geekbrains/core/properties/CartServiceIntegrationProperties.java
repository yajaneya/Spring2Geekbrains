package ru.yajaneya.Spring2Geekbrains.core.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.cart-service")
@Data
@NoArgsConstructor
public class CartServiceIntegrationProperties {
    private String url;
}
