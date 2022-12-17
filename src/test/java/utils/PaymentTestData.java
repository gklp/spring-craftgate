package utils;

import io.craftgate.model.Currency;
import io.craftgate.model.Loyalty;
import io.craftgate.model.PaymentGroup;
import io.craftgate.model.PaymentPhase;
import io.craftgate.request.*;
import io.craftgate.request.dto.Card;
import io.craftgate.request.dto.PaymentItem;
import io.craftgate.response.StoredCardResponse;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class PaymentTestData {

    private PaymentTestData() {
    }

    public static String HOLDER_NAME = "Haluk Demir";
    public static String DEFAULT_CARD_NUMBER = "5258640000000001";
    public static String DEFAULT_CARD_NUMBER_FOR_LOYALTY = "4043080000000003";
    public static String EXPIRE_YEAR = "2044";
    public static String EXPIRE_MONTH = "07";
    public static String CVC = "000";
    public static String ANY_CONVERSATION_ID = "anyConversationId";
    public static String ANY_EXTERNAL_ID = "anyExternalId";

    public static CreatePaymentRequest paymentRequest(String cardNumber) {
        return CreatePaymentRequest.builder()
                .price(BigDecimal.valueOf(90))
                .paidPrice(BigDecimal.valueOf(90))
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardHolderName(HOLDER_NAME)
                        .cardNumber(cardNumber)
                        .expireYear(EXPIRE_YEAR)
                        .expireMonth(EXPIRE_MONTH)
                        .cvc(CVC)
                        .build())
                .items(buildPaymentItems())
                .build();
    }

    public static CreatePaymentRequest paymentRequest() {
        return CreatePaymentRequest.builder()
                .price(BigDecimal.valueOf(90))
                .paidPrice(BigDecimal.valueOf(90))
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardHolderName(HOLDER_NAME)
                        .cardNumber(DEFAULT_CARD_NUMBER)
                        .expireYear(EXPIRE_YEAR)
                        .expireMonth(EXPIRE_MONTH)
                        .cvc(CVC)
                        .build())
                .items(buildPaymentItems())
                .build();
    }

    public static InitThreeDSPaymentRequest threeDSPaymentRequest(String uniqueConversationId, boolean storeCardAfterSuccessPayment) {
        List<PaymentItem> paymentItems = buildPaymentItems();
        BigDecimal paidPrice = paymentItems.stream().map(PaymentItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return InitThreeDSPaymentRequest.builder()
                .price(paidPrice)
                .paidPrice(paidPrice)
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(StringUtils.hasText(uniqueConversationId) ? uniqueConversationId : ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardHolderName(HOLDER_NAME)
                        .cardNumber(DEFAULT_CARD_NUMBER)
                        .expireYear(EXPIRE_YEAR)
                        .expireMonth(EXPIRE_MONTH)
                        .cvc(CVC)
                        .storeCardAfterSuccessPayment(storeCardAfterSuccessPayment)
                        .build())
                .items(paymentItems)
                .callbackUrl("https://www.your-website.com/craftgate-3DSecure-callback")
                .build();
    }

    public static CreatePaymentRequest paymentRequest(boolean storeCardAfterSuccessPayment) {
        return CreatePaymentRequest.builder()
                .price(BigDecimal.valueOf(90))
                .paidPrice(BigDecimal.valueOf(90))
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .storeCardAfterSuccessPayment(storeCardAfterSuccessPayment) //store card
                        .cardHolderName(HOLDER_NAME)
                        .cardNumber(DEFAULT_CARD_NUMBER)
                        .expireYear(EXPIRE_YEAR)
                        .expireMonth(EXPIRE_MONTH)
                        .cvc(CVC)
                        .build())
                .items(buildPaymentItems())
                .build();
    }

    public static CreatePaymentRequest paymentRequest(boolean storeCardAfterSuccessPayment, String cardNo) {
        return CreatePaymentRequest.builder()
                .price(BigDecimal.valueOf(90))
                .paidPrice(BigDecimal.valueOf(90))
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .storeCardAfterSuccessPayment(storeCardAfterSuccessPayment) //store card
                        .cardHolderName(HOLDER_NAME)
                        .cardNumber(cardNo)
                        .expireYear(EXPIRE_YEAR)
                        .expireMonth(EXPIRE_MONTH)
                        .cvc(CVC)
                        .build())
                .items(buildPaymentItems())
                .build();
    }

    public static CreatePaymentRequest paymentRequest(Map<String, Map<String, Object>> additionalParams, String posAlias) {
        return CreatePaymentRequest.builder()
                .price(BigDecimal.valueOf(90))
                .paidPrice(BigDecimal.valueOf(90))
                .walletPrice(BigDecimal.ZERO)
                .posAlias(posAlias)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .externalId(ANY_EXTERNAL_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .items(buildPaymentItems())
                .additionalParams(additionalParams)
                .build();
    }

    public static CreatePaymentRequest paymentRequest(StoredCardResponse storedCardResponse) {
        return CreatePaymentRequest.builder()
                .price(BigDecimal.valueOf(90))
                .paidPrice(BigDecimal.valueOf(90))
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardUserKey(storedCardResponse.getCardUserKey())
                        .cardToken(storedCardResponse.getCardToken())
                        .build())
                .items(buildPaymentItems())
                .build();
    }

    public static CreatePaymentRequest paymentRequest(Loyalty loyalty) {
        List<PaymentItem> paymentItems = buildPaymentItems();
        BigDecimal paidPrice = paymentItems.stream().map(PaymentItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return CreatePaymentRequest.builder()
                .price(paidPrice)
                .paidPrice(paidPrice)
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardHolderName(HOLDER_NAME)
                        .cardNumber(DEFAULT_CARD_NUMBER_FOR_LOYALTY)
                        .expireYear(EXPIRE_YEAR)
                        .expireMonth(EXPIRE_MONTH)
                        .cvc(CVC)
                        .loyalty(loyalty)
                        .build())
                .items(paymentItems)
                .build();

    }

    public static CreatePaymentRequest paymentRequest(String identityNumber, String binNumber, String lastForDigits) {
        List<PaymentItem> paymentItems = buildPaymentItems();
        BigDecimal paidPrice = paymentItems.stream().map(PaymentItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return CreatePaymentRequest.builder()
                .price(paidPrice)
                .paidPrice(paidPrice)
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardHolderIdentityNumber(identityNumber)
                        .binNumber(binNumber)
                        .lastFourDigits(lastForDigits)
                        .build())
                .items(buildPaymentItems())
                .build();

    }

    public static List<PaymentItem> buildPaymentItems() {
        List<PaymentItem> items = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            items.add(PaymentItem.builder()
                    .name("item " + i)
                    .externalId(UUID.randomUUID().toString())
                    .price(BigDecimal.valueOf(30))
                    .build());
        }
        return items;
    }

    public static StoreCardRequest buildStoreCardRequest(String alias) {
        return StoreCardRequest.builder()
                .cardHolderName(HOLDER_NAME)
                .cardNumber(DEFAULT_CARD_NUMBER)
                .expireYear(EXPIRE_YEAR)
                .expireMonth(EXPIRE_MONTH)
                .cardAlias(alias)
                .build();
    }

    public static InitCheckoutPaymentRequest initCheckoutPaymentRequest() {
        return InitCheckoutPaymentRequest.builder()
                .price(BigDecimal.valueOf(90))
                .paidPrice(BigDecimal.valueOf(90))
                .currency(Currency.TRY)
                .conversationId(ANY_CONVERSATION_ID)
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .items(buildPaymentItems())
                .callbackUrl("https://www.your-website.com/craftgate-3DSecure-callback")
                .build();
    }

    public static CreateDepositPaymentRequest depositPaymentRequest(Long buyerMemberId) {
        return CreateDepositPaymentRequest.builder()
                .price(BigDecimal.valueOf(100))
                .buyerMemberId(buyerMemberId)
                .conversationId("456d1297-908e-4bd6-a13b-4be31a6e47d5")
                .card(Card.builder()
                        .cardHolderName(HOLDER_NAME)
                        .cardNumber(DEFAULT_CARD_NUMBER)
                        .expireYear(EXPIRE_YEAR)
                        .expireMonth(EXPIRE_MONTH)
                        .cvc(CVC)
                        .build())
                .build();
    }

}


