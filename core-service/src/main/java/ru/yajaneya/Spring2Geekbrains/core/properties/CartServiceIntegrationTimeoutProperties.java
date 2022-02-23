package ru.yajaneya.Spring2Geekbrains.core.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.cart-service.timeout")
@Data
@NoArgsConstructor
public class CartServiceIntegrationTimeoutProperties {
    private Integer connect;
    private Integer read;
    private Integer write;
}
