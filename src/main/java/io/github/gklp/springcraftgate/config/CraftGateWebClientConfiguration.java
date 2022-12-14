package io.github.gklp.springcraftgate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.craftgate.exception.CraftgateException;
import io.craftgate.model.Loyalty;
import io.craftgate.model.Reward;
import io.craftgate.response.common.ErrorResponse;
import io.github.gklp.springcraftgate.Constants;
import io.github.gklp.springcraftgate.config.mixin.LoyaltyMixIn;
import io.github.gklp.springcraftgate.config.mixin.RewardMixIn;
import io.github.gklp.springcraftgate.config.properties.CraftGateConfigurationProperties;
import io.github.gklp.springcraftgate.config.properties.CraftGateLogConfigurationProperties;
import io.github.gklp.springcraftgate.support.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableConfigurationProperties({CraftGateConfigurationProperties.class, CraftGateLogConfigurationProperties.class})
public class CraftGateWebClientConfiguration {

    private final CraftGateConfigurationProperties craftGateConfigurationProperties;

    private final CraftGateLogConfigurationProperties craftGateLogConfiguration;

    @Bean
    @ConditionalOnMissingBean
    public WebClient craftgateWebClient(WebClient.Builder craftgateWebClientBuilder) {
        return craftgateWebClientBuilder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebClient.Builder craftgateWebClientBuilder(ExchangeStrategies.Builder craftgateExchangeBuilder) {
        WebClient.Builder craftGateWebClientBuilder = WebClient.builder()
                .baseUrl(craftGateConfigurationProperties.getUrl())
                .defaultHeader(Constants.CraftgateHeaders.X_API_KEY.getHeaderKey(), craftGateConfigurationProperties.getKey())
                .defaultHeader(Constants.CraftgateHeaders.X_AUTH_VERSION.getHeaderKey(), craftGateConfigurationProperties.getAuthVersion())
                .defaultHeader(Constants.CraftgateHeaders.X_CLIENT_VERSION.getHeaderKey(), craftGateConfigurationProperties.getClientVersion())
                .exchangeStrategies(craftgateExchangeBuilder.build())
                .filter(ExchangeFilterFunction.ofResponseProcessor(this::errorFilter));

        if (craftGateLogConfiguration.isEnabled()) {
            craftGateWebClientBuilder.filter(logFilter());
        }
        return craftGateWebClientBuilder;
    }

    @Bean
    @ConditionalOnMissingBean
    public ExchangeStrategies.Builder craftgateExchangeBuilder() {
        ObjectMapper craftgateObjectMapper = buildCustomObjectMapper();
        Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(craftgateObjectMapper, MediaType.APPLICATION_JSON);
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(craftgateObjectMapper, MediaType.APPLICATION_JSON);

        return ExchangeStrategies.builder().codecs(configurer -> {
            configurer.defaultCodecs().jackson2JsonEncoder(encoder);
            configurer.defaultCodecs().jackson2JsonDecoder(decoder);
        });
    }

    private Mono<ClientResponse> errorFilter(ClientResponse clientResponse) {
        if (clientResponse.statusCode().isError()) {
            return clientResponse.bodyToMono(ApiResponse.class).flatMap(response -> {
                if (response != null && response.getErrors() != null) {
                    ErrorResponse errors = response.getErrors();
                    CraftgateException craftgateException = new CraftgateException(errors.getErrorCode(), errors.getErrorDescription(), errors.getErrorGroup());
                    return Mono.error(craftgateException);
                }
                return Mono.error(new CraftgateException());
            });
        }
        return Mono.just(clientResponse);
    }

    private ExchangeFilterFunction logFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{} = {}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    private ObjectMapper buildCustomObjectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .addMixIn(Loyalty.class, LoyaltyMixIn.class)
                .addMixIn(Reward.class, RewardMixIn.class)
                .build();
    }

}
