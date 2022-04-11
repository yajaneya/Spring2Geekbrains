package ru.yajaneya.Spring2Geekbrains.core.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.auth-service")
@Data
@NoArgsConstructor
public class AuthServiceIntegrationProperties {
    private String url;
}
