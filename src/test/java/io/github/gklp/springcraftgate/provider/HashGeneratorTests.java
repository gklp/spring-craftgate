package io.github.gklp.springcraftgate.provider;

import io.craftgate.exception.CraftgateException;
import io.github.gklp.springcraftgate.Constants;
import io.github.gklp.springcraftgate.config.properties.CraftGateConfigurationProperties;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import utils.TestConfiguration;
import utils.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = TestConfiguration.class)
public class HashGeneratorTests {

    @Autowired
    private HashGenerator underTest;

    @SpyBean
    private CraftGateConfigurationProperties craftgateConfigurationProperties;

    @RepeatedTest(3)
    public void should_create_hash_value_when_the_request_object_is_not_null() {
        //When
        String hash = underTest.generateHash("anyString", TestData.paymentRequest(), Constants.PaymentPaths.CARD_PAYMENTS.path());

        //Then
        assertThat(hash).isNotNull();
    }

    @RepeatedTest(3)
    public void should_create_hash_value_when_the_request_object_is_null() {
        //When
        String hash = underTest.generateHash("anyString", null, Constants.PaymentPaths.CARD_PAYMENTS.path());

        //Then
        assertThat(hash).isNotNull();
    }

    @RepeatedTest(3)
    public void should_throw_exception_when_the_api_key_is_null() {
        //Given
        Mockito.when(craftgateConfigurationProperties.getKey()).thenReturn(null);

        //When
        assertThatThrownBy(() -> underTest.generateHash("anyString", null, Constants.PaymentPaths.CARD_PAYMENTS.path()))
                .isInstanceOf(CraftgateException.class);
    }

}
