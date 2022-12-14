package io.github.gklp.springcraftgate.adapters;

import io.craftgate.request.*;
import io.craftgate.response.*;
import reactor.core.publisher.Mono;

public interface CraftGatePayment {

    Mono<PaymentResponse> retrievePayment(Long id);

    Mono<PaymentResponse> createPayment(CreatePaymentRequest createPaymentRequest);

    Mono<InitThreeDSPaymentResponse> init3DSPayment(InitThreeDSPaymentRequest initThreeDSPaymentRequest);

    Mono<PaymentResponse> complete3DSPayment(CompleteThreeDSPaymentRequest completeThreeDSPaymentRequest);

    Mono<PaymentResponse> postAuthPayment(long paymentId, PostAuthPaymentRequest postAuthPaymentRequest);

    Mono<InitCheckoutPaymentResponse> initCheckoutPayment(InitCheckoutPaymentRequest initCheckoutPaymentRequest);

    Mono<PaymentResponse> retrieveCheckoutPayment(String token);

    Mono<DepositPaymentResponse> createDepositPayment(CreateDepositPaymentRequest createDepositPaymentRequest);

    Mono<InitThreeDSPaymentResponse> init3DSDepositPayment(CreateDepositPaymentRequest createDepositPaymentRequest);

    Mono<DepositPaymentResponse> complete3DSDepositPayment(CompleteThreeDSPaymentRequest completeThreeDSPaymentRequest);

    Mono<FundTransferDepositPaymentResponse> createFundTransferDepositPayment(CreateFundTransferDepositPaymentRequest createFundTransferDepositPaymentRequest);

    Mono<InitGarantiPayPaymentResponse> initGarantiPayPayment(InitGarantiPayPaymentRequest initGarantiPayPaymentRequest);

    Mono<ApmPaymentInitResponse> initApmPayment(InitApmPaymentRequest initApmPaymentRequest);

    Mono<ApmPaymentCompleteResponse> completeApmPayment(CompleteApmPaymentRequest completeApmPaymentRequest);

    Mono<PaymentResponse> createApmPayment(CreateApmPaymentRequest createApmPaymentRequest);

    Mono<RetrieveLoyaltiesResponse> retrieveLoyalties(RetrieveLoyaltiesRequest retrieveLoyaltiesRequest);

    Mono<PaymentTransactionRefundResponse> refundPaymentTransaction(RefundPaymentTransactionRequest refundPaymentTransactionRequest);

    Mono<PaymentTransactionRefundResponse> retrievePaymentTransactionRefund(Long id);

    Mono<PaymentRefundResponse> refundPayment(RefundPaymentRequest refundPaymentRequest);

    Mono<PaymentRefundResponse> retrievePaymentRefund(Long id);

    Mono<StoredCardResponse> storeCard(StoreCardRequest storeCardRequest);

    Mono<StoredCardResponse> updateCard(UpdateCardRequest updateCardRequest);

    Mono<StoredCardListResponse> searchStoredCards(SearchStoredCardsRequest searchStoredCardsRequest);

    void deleteStoredCard(DeleteStoredCardRequest deleteStoredCardRequest);

    Mono<PaymentTransactionApprovalListResponse> approvePaymentTransactions(ApprovePaymentTransactionsRequest approvePaymentTransactionsRequest);

    Mono<PaymentTransactionApprovalListResponse> disapprovePaymentTransactions(DisapprovePaymentTransactionsRequest disapprovePaymentTransactionsRequest);

    Mono<CheckMasterpassUserResponse> checkMasterPassUser(CheckMasterpassUserRequest checkMasterpassUserRequest);

    Mono<PaymentTransactionResponse> updatePaymentTransaction(UpdatePaymentTransactionRequest updatePaymentTransactionRequest);
}
