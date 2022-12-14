package io.gklp.craftgate.config;

import io.gklp.craftgate.provider.RandomKeyProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import utils.TestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

public class CraftGateAutoConfigurationTests {

    @SpringBootTest(classes = TestConfiguration.class)
    @TestPropertySource(properties = "craftgate.enabled=true")
    static class CraftGateAutoConfigurationEnabledTests {

        @Autowired
        private ApplicationContext applicationContext;

        @Test
        public void should_get_craftgate_auto_configuration_as_bean() {
            //When
            CraftGateAutoConfiguration result = applicationContext.getBean("io.gklp.craftgate.config.CraftGateAutoConfiguration",
                    CraftGateAutoConfiguration.class);

            //Then
            assertThat(result).isNotNull();
        }

        @Test
        public void should_get_craftgate_random_key_provider_as_bean() {
            //When
            RandomKeyProvider result = applicationContext.getBean("craftgateRandomKeyProvider", RandomKeyProvider.class);

            //Then
            assertThat(result).isNotNull();
        }
    }

    @SpringBootTest(classes = TestConfiguration.class)
    @TestPropertySource(properties = "craftgate.enabled=false")
    static class CraftGateAutoConfigurationDisabledTests {

        @Autowired
        private ApplicationContext applicationContext;

        @Test
        public void should_not_exist_craftgate_auto_configuration_as_bean() {
            //Then
            assertThat(applicationContext.containsBean("io.gklp.craftgate.config.CraftGateAutoConfiguration")).isFalse();
        }

        @Test
        public void should_not_exist_craftgate_random_key_provider_as_bean() {
            //Then
            assertThat(applicationContext.containsBean("craftgateRandomKeyProvider")).isFalse();
        }
    }

}
