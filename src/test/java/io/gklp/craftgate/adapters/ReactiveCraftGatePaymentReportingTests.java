package io.gklp.craftgate.adapters;

import io.craftgate.request.InitThreeDSPaymentRequest;
import io.craftgate.request.SearchPaymentsRequest;
import io.craftgate.response.InitThreeDSPaymentResponse;
import io.craftgate.response.ReportingPaymentListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.TestConfiguration;
import utils.TestData;
import utils.TestUtil;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestConfiguration.class)
public class ReactiveCraftGatePaymentReportingTests {

    @Autowired
    private CraftGatePayment underTest;

    @Autowired
    private CraftGatePaymentReporting craftgatePaymentReporting;

    @Test
    void should_find_payment_using_conversion_id() throws IOException {
        //Given
        String conversationId = UUID.randomUUID().toString();
        InitThreeDSPaymentRequest initThreeDSPaymentRequest = TestData.threeDSPaymentRequest(conversationId, false);
        InitThreeDSPaymentResponse initThreeDSPaymentResponse = underTest.init3DSPayment(initThreeDSPaymentRequest).block();

        assert initThreeDSPaymentResponse != null;
        TestUtil.approveFakeConfirmation(initThreeDSPaymentResponse.getDecodedHtmlContent());

        //When
        ReportingPaymentListResponse paymentListResponse =
                craftgatePaymentReporting.searchPayments(SearchPaymentsRequest.builder().conversationId(conversationId).build()).block();

        //Then
        assertNotNull(paymentListResponse);
        assertThat(paymentListResponse.getItems()).isNotNull();
        assertThat(paymentListResponse.getItems().get(0).getId()).isNotNull();
        assertThat(paymentListResponse.getItems().size()).isEqualTo(1);
    }

}
