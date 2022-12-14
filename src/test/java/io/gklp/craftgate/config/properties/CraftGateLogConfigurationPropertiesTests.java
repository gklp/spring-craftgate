package io.gklp.craftgate.config.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.TestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
public class CraftGateLogConfigurationPropertiesTests {

    @Autowired
    private CraftGateLogConfigurationProperties underTest;

    @Test
    public void should_return_log_request_enabled_false_as_default() {
        //When
        Boolean result = underTest.isEnabled();

        //Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(false);
    }

}
