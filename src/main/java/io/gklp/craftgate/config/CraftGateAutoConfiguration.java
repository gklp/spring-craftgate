package io.gklp.craftgate.config;

import io.gklp.craftgate.provider.RandomKeyProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@ConditionalOnProperty(value = "craftgate.enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan("io.gklp.craftgate")
public class CraftGateAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RandomKeyProvider craftgateRandomKeyProvider() {
        return () -> UUID.randomUUID().toString();
    }

}
