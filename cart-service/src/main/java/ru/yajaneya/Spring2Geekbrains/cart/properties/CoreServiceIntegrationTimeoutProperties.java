package ru.yajaneya.Spring2Geekbrains.cart.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.core-service.timeout")
@Data
@NoArgsConstructor
public class CoreServiceIntegrationTimeoutProperties {
    private Integer connect;
    private Integer read;
    private Integer write;
}
