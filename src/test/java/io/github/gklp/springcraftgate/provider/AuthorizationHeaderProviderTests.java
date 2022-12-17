package io.github.gklp.springcraftgate.provider;

import io.craftgate.request.CreatePaymentRequest;
import io.github.gklp.springcraftgate.Constants;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import utils.PaymentTestData;
import utils.TestConfiguration;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfiguration.class)
public class AuthorizationHeaderProviderTests {

    @MockBean
    private RandomKeyProvider craftgateRandomKeyProvider;

    @MockBean
    private HashGenerator hashGenerator;

    @Autowired
    private AuthorizationHeaderProvider underTest;

    @RepeatedTest(3)
    public void should_return_signature_headers() {
        //Given
        String randomString = "anyRandomString";
        String signature = "anyHashedSignature";
        CreatePaymentRequest paymentRequest = PaymentTestData.paymentRequest();

        when(craftgateRandomKeyProvider.generate()).thenReturn(randomString);
        when(hashGenerator.generateHash(randomString, paymentRequest, Constants.PaymentPaths.CARD_PAYMENTS.path())).thenReturn(signature);

        //When
        Consumer<HttpHeaders> headers = underTest.buildSignature(paymentRequest, Constants.PaymentPaths.CARD_PAYMENTS.path());
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.accept(httpHeaders);

        //Then
        assertThat(headers).isNotNull();
        assertThat(httpHeaders).isNotNull();
        assertThat(httpHeaders.size()).isEqualTo(2);
        assertThat(httpHeaders.get(Constants.CraftgateHeaders.X_RND_KEY.getHeaderKey())).isNotNull();
        assertThat(httpHeaders.get(Constants.CraftgateHeaders.X_SIGNATURE.getHeaderKey())).isNotNull();
        assertThat(httpHeaders.get(Constants.CraftgateHeaders.X_RND_KEY.getHeaderKey())).contains(randomString);
        assertThat(httpHeaders.get(Constants.CraftgateHeaders.X_SIGNATURE.getHeaderKey())).contains(signature);

        verify(craftgateRandomKeyProvider).generate();
        verify(hashGenerator).generateHash(randomString, paymentRequest, Constants.PaymentPaths.CARD_PAYMENTS.path());
    }

    @RepeatedTest(3)
    public void should_return_signature_headers_if_the_request_object_is_not_exist() {
        //Given
        String randomString = "anyRandomString";
        String signature = "anyHashedSignature";

        when(craftgateRandomKeyProvider.generate()).thenReturn(randomString);
        when(hashGenerator.generateHash(randomString, null, Constants.PaymentPaths.CARD_PAYMENTS.path())).thenReturn(signature);

        //When
        Consumer<HttpHeaders> headers = underTest.buildSignature(Constants.PaymentPaths.CARD_PAYMENTS.path());
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.accept(httpHeaders);

        //Then
        assertThat(headers).isNotNull();
        assertThat(httpHeaders).isNotNull();
        assertThat(httpHeaders.size()).isEqualTo(2);
        assertThat(httpHeaders.get(Constants.CraftgateHeaders.X_RND_KEY.getHeaderKey())).isNotNull();
        assertThat(httpHeaders.get(Constants.CraftgateHeaders.X_SIGNATURE.getHeaderKey())).isNotNull();
        assertThat(httpHeaders.get(Constants.CraftgateHeaders.X_RND_KEY.getHeaderKey())).contains(randomString);
        assertThat(httpHeaders.get(Constants.CraftgateHeaders.X_SIGNATURE.getHeaderKey())).contains(signature);

        verify(craftgateRandomKeyProvider).generate();
        verify(hashGenerator).generateHash(randomString, null, Constants.PaymentPaths.CARD_PAYMENTS.path());
    }

}
