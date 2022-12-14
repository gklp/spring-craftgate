package io.github.gklp.springcraftgate.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import utils.TestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

public class CraftGateWebClientConfigurationTests {

    @SpringBootTest(classes = TestConfiguration.class)
    @TestPropertySource(properties = "craftgate.enabled=true")
    static class CraftGateWebClientConfigurationEnabledTests {

        @Autowired
        private ApplicationContext applicationContext;

        @Test
        public void should_get_craftgate_webclient_config_as_bean() {
            //When
            CraftGateWebClientConfiguration result = applicationContext.getBean("craftGateWebClientConfiguration", CraftGateWebClientConfiguration.class);

            //Then
            assertThat(result).isNotNull();
        }

        @Test
        public void should_get_craftgate_webclient_as_bean() {
            //When
            WebClient result = applicationContext.getBean("craftgateWebClient", WebClient.class);

            //Then
            assertThat(result).isNotNull();
        }

        @Test
        public void should_call_api_with_proper_headers_successfully() {
            //GIVEN
            WebClient webClient = applicationContext.getBean("craftgateWebClient", WebClient.class);

            //When
            String result = webClient.get()
                    .uri("https://sandbox-api.craftgate.io/status")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            //Then
            assertThat(result).isEqualTo("ok");
        }

        @Test
        public void should_get_craftgate_exchange_builder_as_bean() {
            //When
            ExchangeStrategies.Builder result = applicationContext.getBean("craftgateExchangeBuilder", ExchangeStrategies.Builder.class);

            //Then
            assertThat(result).isNotNull();
        }

        @Test
        public void should_get_craftgate_webclient_builder_as_bean() {
            //When
            WebClient.Builder result = applicationContext.getBean("craftgateWebClientBuilder", WebClient.Builder.class);

            //Then
            assertThat(result).isNotNull();
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    @TestPropertySource(properties = "craftgate.enabled=false")
    static class CraftGateWebClientConfigurationDisabledTests {

        @Autowired
        private ApplicationContext applicationContext;

        @Test
        public void should_not_exist_craftgate_webclient_config_as_bean() {
            //Then
            assertThat(applicationContext.containsBean("craftGateWebClientConfiguration")).isFalse();
        }

        @Test
        public void should_not_exist_craftgate_webclient_as_bean() {
            //Then
            assertThat(applicationContext.containsBean("craftgateWebClient")).isFalse();
        }

        @Test
        public void should_not_exist_craftgate_exchange_builder_as_bean() {
            //Then
            assertThat(applicationContext.containsBean("craftgateExchangeBuilder")).isFalse();
        }

        @Test
        public void should_not_exist_craftgate_webclient_builder_as_bean() {
            //Then
            assertThat(applicationContext.containsBean("craftgateWebClientBuilder")).isFalse();
        }
    }

}
