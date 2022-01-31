package ru.yajaneya.Spring2Geekbrains.core.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"secrets.properties"})
public class AppConfig {
}
