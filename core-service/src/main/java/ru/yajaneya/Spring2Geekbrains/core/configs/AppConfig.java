package ru.yajaneya.Spring2Geekbrains.core.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.yajaneya.Spring2Geekbrains.core.properties.*;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({
        AuthServiceIntegrationProperties.class,
        AuthServiceIntegrationTimeoutProperties.class,
        CartServiceIntegrationProperties.class,
        CartServiceIntegrationTimeoutProperties.class,
        RecomServiceIntegrationProperties.class,
        RecomServiceIntegrationTimeoutProperties.class
})
@RequiredArgsConstructor
public class AppConfig {
    private final AuthServiceIntegrationProperties authServiceIntegrationProperties;
    private final AuthServiceIntegrationTimeoutProperties authServiceIntegrationTimeoutProperties;
    private final CartServiceIntegrationProperties cartServiceIntegrationProperties;
    private final CartServiceIntegrationTimeoutProperties cartServiceIntegrationTimeoutProperties;
    private final RecomServiceIntegrationProperties recomServiceIntegrationProperties;
    private final RecomServiceIntegrationTimeoutProperties recomServiceIntegrationTimeoutProperties;

    @Bean
    public WebClient authServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        authServiceIntegrationTimeoutProperties.getConnect())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(
                            authServiceIntegrationTimeoutProperties.getRead(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(
                            authServiceIntegrationTimeoutProperties.getWrite(),
                            TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(authServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
    @Bean
    public WebClient cartServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        cartServiceIntegrationTimeoutProperties.getConnect())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(
                            cartServiceIntegrationTimeoutProperties.getRead(),
                            TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(
                            cartServiceIntegrationTimeoutProperties.getWrite(),
                            TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(cartServiceIntegrationProperties.getUrl())
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
