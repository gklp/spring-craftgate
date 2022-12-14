package io.github.gklp.springcraftgate.config.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.TestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
public class CraftGateConfigurationPropertiesTests {

    @Autowired
    private CraftGateConfigurationProperties underTest;

    @Test
    public void should_return_auth_version() {
        //When
        String result = underTest.getAuthVersion();

        //Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("v1");
    }

    @Test
    public void should_return_client_version() {
        //When
        String result = underTest.getClientVersion();

        //Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("craftgate-java-client:1.0.33");
    }

    @Test
    public void should_return_key() {
        //When
        String result = underTest.getKey();

        //Then
        assertThat(result).isNotNull();
    }

    @Test
    public void should_return_secret() {
        //When
        String result = underTest.getSecret();

        //Then
        assertThat(result).isNotNull();
    }

}
