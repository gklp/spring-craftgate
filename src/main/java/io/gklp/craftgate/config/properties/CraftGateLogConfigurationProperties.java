package io.gklp.craftgate.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("craftgate.log")
@Setter
@Getter
public class CraftGateLogConfigurationProperties {

    private boolean enabled;

}
