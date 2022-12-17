package io.github.gklp.springcraftgate.adapters;

import io.craftgate.model.*;
import io.craftgate.request.*;
import io.craftgate.response.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.PaymentTestData;
import utils.TestConfiguration;
import utils.TestUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfiguration.class)
class ReactiveCraftGatePaymentTests {

    @Autowired
    private CraftGatePayment underTest;

    @Autowired
    private CraftGatePaymentReporting craftgatePaymentReporting;

    @Test
    void should_create_payment_successfully() {
        //Given
        CreatePaymentRequest request = PaymentTestData.paymentRequest();

        //When
        PaymentResponse response = underTest.createPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNull(response.getCardUserKey());
        assertNull(response.getCardToken());
        assertNull(response.getFraudId());
        assertNull(response.getFraudAction());
        assertNull(response.getPaymentError());
        assertThat(request.getPrice()).isEqualByComparingTo(response.getPrice());
        assertThat(request.getPaidPrice()).isEqualByComparingTo(response.getPaidPrice());
        assertThat(request.getWalletPrice()).isEqualByComparingTo(response.getWalletPrice());
        assertThat(request.getCurrency()).isEqualTo(response.getCurrency());
        assertThat(request.getInstallment()).isEqualTo(response.getInstallment());
        assertThat(PaymentSource.API).isEqualTo(response.getPaymentSource());
        assertThat(request.getPaymentGroup()).isEqualTo(response.getPaymentGroup());
        assertThat(request.getPaymentPhase()).isEqualTo(response.getPaymentPhase());
        assertThat(response.getIsThreeDS()).isEqualTo(false);
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRate());
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRateAmount());
        assertThat(false).isEqualTo(response.getPaidWithStoredCard());
        assertThat("52586400").isEqualTo(response.getBinNumber());
        assertThat("0001").isEqualTo(response.getLastFourDigits());
        assertThat(CardType.CREDIT_CARD).isEqualTo(response.getCardType());
        assertThat(CardAssociation.MASTER_CARD).isEqualTo(response.getCardAssociation());
        assertThat("World").isEqualTo(response.getCardBrand());
        assertThat(3).isEqualTo(response.getPaymentTransactions().size());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testCards.csv", numLinesToSkip = 1)
    void should_create_payment_with_multiple_cards_successfully(String cardNo, String binNumber, String lastFourDigits,
                                                                String cardType, String cardBrand, String cardAssociation) {
        //Given
        CreatePaymentRequest request = PaymentTestData.paymentRequest(cardNo);

        //When
        PaymentResponse response = underTest.createPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNull(response.getCardUserKey());
        assertNull(response.getCardToken());
        assertNull(response.getFraudId());
        assertNull(response.getFraudAction());
        assertNull(response.getPaymentError());
        assertThat(request.getPrice()).isEqualByComparingTo(response.getPrice());
        assertThat(request.getPaidPrice()).isEqualByComparingTo(response.getPaidPrice());
        assertThat(request.getWalletPrice()).isEqualByComparingTo(response.getWalletPrice());
        assertThat(request.getCurrency()).isEqualTo(response.getCurrency());
        assertThat(request.getInstallment()).isEqualTo(response.getInstallment());
        assertThat(PaymentSource.API).isEqualTo(response.getPaymentSource());
        assertThat(request.getPaymentGroup()).isEqualTo(response.getPaymentGroup());
        assertThat(request.getPaymentPhase()).isEqualTo(response.getPaymentPhase());
        assertThat(response.getIsThreeDS()).isEqualTo(false);
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRate());
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRateAmount());
        assertThat(false).isEqualTo(response.getPaidWithStoredCard());
        assertThat(binNumber).isEqualTo(response.getBinNumber());
        assertThat(lastFourDigits).isEqualTo(response.getLastFourDigits());
        assertThat(CardType.valueOf(cardType)).isEqualTo(response.getCardType());
        assertThat(CardAssociation.valueOf(cardAssociation)).isEqualTo(response.getCardAssociation());
        assertThat(cardBrand).isEqualTo(response.getCardBrand());
        assertThat(3).isEqualTo(response.getPaymentTransactions().size());
    }

    @Test
    void create_payment_and_store_card() {
        //Given
        CreatePaymentRequest request = PaymentTestData.paymentRequest(true);

        //When
        PaymentResponse response = underTest.createPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertThat(request.getPrice()).isEqualByComparingTo(response.getPrice());
        assertThat(request.getPaidPrice()).isEqualByComparingTo(response.getPaidPrice());
        assertEquals(request.getWalletPrice(), response.getWalletPrice());
        assertEquals(request.getCurrency(), response.getCurrency());
        assertEquals(request.getInstallment(), response.getInstallment());
        assertEquals(request.getPaymentGroup(), response.getPaymentGroup());
        assertEquals(request.getPaymentPhase(), response.getPaymentPhase());
        assertEquals(request.getConversationId(), response.getConversationId());
        assertEquals(request.getExternalId(), response.getExternalId());
        assertEquals(false, response.getIsThreeDS());
        assertEquals(BigDecimal.ZERO, response.getMerchantCommissionRate());
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRateAmount());
        assertEquals(false, response.getPaidWithStoredCard());
        assertEquals("52586400", response.getBinNumber());
        assertEquals("0001", response.getLastFourDigits());
        assertEquals(CardType.CREDIT_CARD, response.getCardType());
        assertEquals(CardAssociation.MASTER_CARD, response.getCardAssociation());
        assertEquals("World", response.getCardBrand());
        assertEquals(3, response.getPaymentTransactions().size());
        assertNotNull(response.getCardUserKey());
        assertNotNull(response.getCardToken());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testCards.csv", numLinesToSkip = 1)
    void create_payment_and_store_card_with_multiple_cards_successfully(String cardNo, String binNumber, String lastFourDigits,
                                                                        String cardType, String cardBrand, String cardAssociation) {
        //Given
        CreatePaymentRequest request = PaymentTestData.paymentRequest(true, cardNo);

        //When
        PaymentResponse response = underTest.createPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertThat(request.getPrice()).isEqualByComparingTo(response.getPrice());
        assertThat(request.getPaidPrice()).isEqualByComparingTo(response.getPaidPrice());
        assertEquals(request.getWalletPrice(), response.getWalletPrice());
        assertEquals(request.getCurrency(), response.getCurrency());
        assertEquals(request.getInstallment(), response.getInstallment());
        assertEquals(request.getPaymentGroup(), response.getPaymentGroup());
        assertEquals(request.getPaymentPhase(), response.getPaymentPhase());
        assertEquals(request.getConversationId(), response.getConversationId());
        assertEquals(request.getExternalId(), response.getExternalId());
        assertEquals(false, response.getIsThreeDS());
        assertEquals(BigDecimal.ZERO, response.getMerchantCommissionRate());
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRateAmount());
        assertEquals(false, response.getPaidWithStoredCard());
        assertEquals(binNumber, response.getBinNumber());
        assertEquals(lastFourDigits, response.getLastFourDigits());
        assertThat(CardType.valueOf(cardType)).isEqualTo(response.getCardType());
        assertThat(CardAssociation.valueOf(cardAssociation)).isEqualTo(response.getCardAssociation());
        assertThat(cardBrand).isEqualTo(response.getCardBrand());
        assertEquals(3, response.getPaymentTransactions().size());
        assertNotNull(response.getCardUserKey());
        assertNotNull(response.getCardToken());
    }

    @Test
    void create_payment_using_stored_card() {
        //Given
        final StoreCardRequest storeCardRequest = PaymentTestData.buildStoreCardRequest("myCard");
        StoredCardResponse storedCardResponse = underTest.storeCard(storeCardRequest).block(); //create store card

        assert storedCardResponse != null;
        final CreatePaymentRequest request = PaymentTestData.paymentRequest(storedCardResponse);

        //When
        PaymentResponse response = underTest.createPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertThat(request.getPrice()).isEqualByComparingTo(response.getPrice());
        assertThat(request.getPaidPrice()).isEqualByComparingTo(response.getPaidPrice());
        assertEquals(request.getWalletPrice(), response.getWalletPrice());
        assertEquals(request.getCurrency(), response.getCurrency());
        assertEquals(request.getInstallment(), response.getInstallment());
        assertEquals(request.getPaymentGroup(), response.getPaymentGroup());
        assertEquals(request.getPaymentPhase(), response.getPaymentPhase());
        assertEquals(false, response.getIsThreeDS());
        assertEquals(BigDecimal.ZERO, response.getMerchantCommissionRate());
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRateAmount());
        assertEquals(true, response.getPaidWithStoredCard());
        assertEquals("52586400", response.getBinNumber());
        assertEquals("0001", response.getLastFourDigits());
        assertEquals(CardType.CREDIT_CARD, response.getCardType());
        assertEquals(CardAssociation.MASTER_CARD, response.getCardAssociation());
        assertEquals("World", response.getCardBrand());
        assertEquals(3, response.getPaymentTransactions().size());
        assertNull(response.getCardUserKey());
        assertNull(response.getCardToken());
    }

    @Test
    @Disabled
    void create_payment_using_external_payment_provider_stored_card() {
        //Given
        Map<String, Map<String, Object>> additionalParams = new HashMap<>();
        Map<String, Object> paymentProviderParams = new HashMap<String, Object>() {{
            put("cardUserKey", "test-cardUserKey");
            put("cardToken", "tuz8imxv30");
        }};
        additionalParams.put("paymentProvider", paymentProviderParams);
        CreatePaymentRequest request = PaymentTestData.paymentRequest(additionalParams, "62-garanti-357");

        //When
        PaymentResponse response = underTest.createPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertThat(request.getPrice()).isEqualByComparingTo(response.getPrice());
        assertThat(request.getPaidPrice()).isEqualByComparingTo(response.getPaidPrice());
        assertEquals(request.getWalletPrice(), response.getWalletPrice());
        assertEquals(request.getCurrency(), response.getCurrency());
        assertEquals(request.getInstallment(), response.getInstallment());
        assertEquals(request.getPaymentGroup(), response.getPaymentGroup());
        assertEquals(request.getPaymentPhase(), response.getPaymentPhase());
        assertEquals(request.getExternalId(), response.getExternalId());
        assertEquals(false, response.getIsThreeDS());
        assertEquals(BigDecimal.ZERO, response.getMerchantCommissionRate());
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRateAmount());
        assertEquals(false, response.getPaidWithStoredCard());
        assertEquals(3, response.getPaymentTransactions().size());
        assertNull(response.getBinNumber());
        assertNull(response.getLastFourDigits());
        assertNull(response.getCardType());
        assertNull(response.getCardAssociation());
        assertNull(response.getCardBrand());
        assertNull(response.getCardUserKey());
        assertNull(response.getCardToken());
    }

    @Test
    void create_payment_with_loyalty() {
        //Given
        Loyalty loyalty = Loyalty.builder()
                .type(LoyaltyType.REWARD_MONEY)
                .reward(Reward.builder()
                        .cardRewardMoney(new BigDecimal("1.36"))
                        .firmRewardMoney(new BigDecimal("3.88"))
                        .build()
                )
                .build();

        //When
        CreatePaymentRequest request = PaymentTestData.paymentRequest(loyalty);

        //Then
        PaymentResponse response = underTest.createPayment(request).block();
        assertNotNull(response);
        assertNotNull(response.getId());
        assertThat(request.getPrice()).isEqualByComparingTo(response.getPrice());
        assertThat(request.getPaidPrice()).isEqualByComparingTo(response.getPaidPrice());
        assertEquals(request.getWalletPrice(), response.getWalletPrice());
        assertEquals(request.getCurrency(), response.getCurrency());
        assertEquals(request.getInstallment(), response.getInstallment());
        assertEquals(PaymentSource.API, response.getPaymentSource());
        assertEquals(request.getPaymentGroup(), response.getPaymentGroup());
        assertEquals(request.getPaymentPhase(), response.getPaymentPhase());
        assertEquals(false, response.getIsThreeDS());
        assertEquals(BigDecimal.ZERO, response.getMerchantCommissionRate());
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRateAmount());
        assertEquals(false, response.getPaidWithStoredCard());
        assertEquals("40430800", response.getBinNumber());
        assertEquals("0003", response.getLastFourDigits());
        assertEquals(CardType.CREDIT_CARD, response.getCardType());
        assertEquals(CardAssociation.VISA, response.getCardAssociation());
        assertEquals("Bonus", response.getCardBrand());
        assertEquals(3, response.getPaymentTransactions().size());
        assertNull(response.getCardUserKey());
        assertNull(response.getCardToken());
        assertNull(response.getPaymentError());
        assertNotNull(response.getLoyalty());
        assertEquals(LoyaltyType.REWARD_MONEY, response.getLoyalty().getType());
        assertNotNull(response.getLoyalty().getReward());
        assertThat(new BigDecimal("1.36")).isEqualByComparingTo(response.getLoyalty().getReward().getCardRewardMoney());
        assertThat(new BigDecimal("3.88")).isEqualByComparingTo(response.getLoyalty().getReward().getFirmRewardMoney());
    }

    @Test
    void create_payment_with_first6_last4_and_identityNumber() {
        //Given
        CreatePaymentRequest request = PaymentTestData.paymentRequest("12345678900", "404308", "0003");

        //When
        PaymentResponse response = underTest.createPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getId());
        assertThat(request.getPrice()).isEqualByComparingTo(response.getPrice());
        assertThat(request.getPaidPrice()).isEqualByComparingTo(response.getPaidPrice());
        assertEquals(request.getWalletPrice(), response.getWalletPrice());
        assertEquals(request.getCurrency(), response.getCurrency());
        assertEquals(request.getInstallment(), response.getInstallment());
        assertEquals(PaymentSource.API, response.getPaymentSource());
        assertEquals(request.getPaymentGroup(), response.getPaymentGroup());
        assertEquals(request.getPaymentPhase(), response.getPaymentPhase());
        assertEquals(false, response.getIsThreeDS());
        assertEquals(BigDecimal.ZERO, response.getMerchantCommissionRate());
        assertThat(BigDecimal.ZERO).isEqualByComparingTo(response.getMerchantCommissionRateAmount());
        assertEquals(false, response.getPaidWithStoredCard());
        assertEquals("404308**", response.getBinNumber());
        assertEquals("0003", response.getLastFourDigits());
        assertEquals(CardType.CREDIT_CARD, response.getCardType());
        assertEquals(CardAssociation.VISA, response.getCardAssociation());
        assertEquals("Bonus", response.getCardBrand());
        assertEquals(3, response.getPaymentTransactions().size());
        assertNull(response.getCardUserKey());
        assertNull(response.getCardToken());
        assertNull(response.getPaymentError());
    }

    @Test
    void init_3DS_payment() {
        //Given
        InitThreeDSPaymentRequest request = PaymentTestData.threeDSPaymentRequest(null, false);

        //When
        InitThreeDSPaymentResponse response = underTest.init3DSPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getHtmlContent());
        assertNotNull(response.getDecodedHtmlContent());
    }

    @Test
    void init_3DS_payment_and_store_card() {
        //Given
        InitThreeDSPaymentRequest request = PaymentTestData.threeDSPaymentRequest(null, true);

        //When
        InitThreeDSPaymentResponse response = underTest.init3DSPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getHtmlContent());
        assertNotNull(response.getDecodedHtmlContent());
    }

    @Test
    void init_3DS_payment_using_stored_card() {
        //Given
        final StoreCardRequest storeCardRequest = PaymentTestData.buildStoreCardRequest("myCard");
        StoredCardResponse storedCardResponse = underTest.storeCard(storeCardRequest).block(); //create store card

        assert storedCardResponse != null;
        final InitThreeDSPaymentRequest request = PaymentTestData.threeDSPaymentRequest(null, false);

        //When
        InitThreeDSPaymentResponse response = underTest.init3DSPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getHtmlContent());
        assertNotNull(response.getDecodedHtmlContent());
    }

    @Test
    void complete_3DS_payment() throws IOException {
        //Given
        String conversationId = UUID.randomUUID().toString();
        InitThreeDSPaymentRequest initThreeDSPaymentRequest = PaymentTestData.threeDSPaymentRequest(conversationId, false);
        InitThreeDSPaymentResponse initThreeDSPaymentResponse = underTest.init3DSPayment(initThreeDSPaymentRequest).block();

        assert initThreeDSPaymentResponse != null;
        TestUtil.approveFakeConfirmation(initThreeDSPaymentResponse.getDecodedHtmlContent());

        ReportingPaymentListResponse paymentListResponse =
                craftgatePaymentReporting.searchPayments(SearchPaymentsRequest.builder().conversationId(conversationId).build()).block();

        assert paymentListResponse != null;
        CompleteThreeDSPaymentRequest request = CompleteThreeDSPaymentRequest.builder()
                .paymentId(paymentListResponse.getItems().stream().findFirst().get().getId())
                .build();

        //When
        PaymentResponse response = underTest.complete3DSPayment(request).block();

        //Then
        assertNotNull(response);
    }

    @Test
    void init_checkout_payment() {
        //Given
        InitCheckoutPaymentRequest request = PaymentTestData.initCheckoutPaymentRequest();

        //When
        InitCheckoutPaymentResponse response = underTest.initCheckoutPayment(request).block();

        //Then
        assertNotNull(response);
        assertNotNull(response.getPageUrl());
        assertNotNull(response.getToken());
    }


}