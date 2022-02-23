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
import ru.yajaneya.Spring2Geekbrains.cart.properties.CoreServiceIntegrationTimeoutProperties;
import ru.yajaneya.Spring2Geekbrains.cart.properties.RecomServiceIntegrationProperties;
import ru.yajaneya.Spring2Geekbrains.cart.properties.RecomServiceIntegrationTimeoutProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({
        CoreServiceIntegrationProperties.class,
        CoreServiceIntegrationTimeoutProperties.class,
        RecomServiceIntegrationProperties.class,
        RecomServiceIntegrationTimeoutProperties.class
})
@RequiredArgsConstructor
public class AppConfig {
    private final CoreServiceIntegrationProperties coreServiceIntegrationProperties;
    private final CoreServiceIntegrationTimeoutProperties coreServiceIntegrationTimeoutProperties;
    private final RecomServiceIntegrationProperties recomServiceIntegrationProperties;
    private final RecomServiceIntegrationTimeoutProperties recomServiceIntegrationTimeoutProperties;


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
                        coreServiceIntegrationTimeoutProperties.getConnect())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(
                            coreServiceIntegrationTimeoutProperties.getRead(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(
                            coreServiceIntegrationTimeoutProperties.getWrite(),
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
                        recomServiceIntegrationTimeoutProperties.getConnect())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(
                            recomServiceIntegrationTimeoutProperties.getRead(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(
                            recomServiceIntegrationTimeoutProperties.getWrite(),
                            TimeUnit.MILLISECONDS));
                });


        return WebClient
                .builder()
                .baseUrl(recomServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

}
