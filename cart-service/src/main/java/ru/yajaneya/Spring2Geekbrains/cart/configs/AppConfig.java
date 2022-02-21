package ru.yajaneya.Spring2Geekbrains.cart.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.yajaneya.Spring2Geekbrains.cart.properties.CoreServiceIntegrationProperties;
import ru.yajaneya.Spring2Geekbrains.cart.properties.RecomServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(
        {CoreServiceIntegrationProperties.class, RecomServiceIntegrationProperties.class}
)
@RequiredArgsConstructor
public class AppConfig {
    private final CoreServiceIntegrationProperties coreServiceIntegrationProperties;
    private final RecomServiceIntegrationProperties recomServiceIntegrationProperties;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient coreServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        coreServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(
                            coreServiceIntegrationProperties.getReadTimeout(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(
                            coreServiceIntegrationProperties.getWriteTimeout(),
                            TimeUnit.MILLISECONDS));
                });


        return WebClient
                .builder()
                .baseUrl(coreServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @Bean
    public WebClient recomServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        recomServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(
                            recomServiceIntegrationProperties.getReadTimeout(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(
                            recomServiceIntegrationProperties.getWriteTimeout(),
                            TimeUnit.MILLISECONDS));
                });


        return WebClient
                .builder()
                .baseUrl(recomServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

}
