package io.github.gklp.springcraftgate.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("craftgate.api")
@Setter
@Getter
@Validated
public class CraftGateConfigurationProperties {

    @NotBlank
    private String url;

    @NotBlank
    private String key;

    @NotBlank
    private String secret;

    private String authVersion = "v1";

    private String clientVersion = "craftgate-java-client:1.0.33";

}
