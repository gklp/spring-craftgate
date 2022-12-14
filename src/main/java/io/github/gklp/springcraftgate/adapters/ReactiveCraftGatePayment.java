package io.github.gklp.springcraftgate.adapters;

import io.craftgate.request.*;
import io.craftgate.request.common.RequestQueryParamsBuilder;
import io.craftgate.response.*;
import io.github.gklp.springcraftgate.provider.AuthorizationHeaderProvider;
import io.github.gklp.springcraftgate.support.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static io.github.gklp.springcraftgate.Constants.PaymentPaths.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReactiveCraftGatePayment implements CraftGatePayment {

    private final WebClient craftgateWebClient;

    private final AuthorizationHeaderProvider authorizationHeaderProvider;

    @Override
    public Mono<PaymentResponse> createPayment(CreatePaymentRequest createPaymentRequest) {
        return craftgateWebClient.post()
                .uri(CARD_PAYMENTS.path())
                .headers(authorizationHeaderProvider.buildSignature(createPaymentRequest, CARD_PAYMENTS.path()))
                .bodyValue(createPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentResponse> retrievePayment(Long id) {
        return craftgateWebClient.get()
                .uri(CARD_PAYMENTS_RETRIEVE.path(), id)
                .headers(authorizationHeaderProvider.buildSignature(CARD_PAYMENTS_RETRIEVE.path()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<InitThreeDSPaymentResponse> init3DSPayment(InitThreeDSPaymentRequest initThreeDSPaymentRequest) {
        return craftgateWebClient.post()
                .uri(INIT_3D.path())
                .headers(authorizationHeaderProvider.buildSignature(initThreeDSPaymentRequest, INIT_3D.path()))
                .bodyValue(initThreeDSPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<InitThreeDSPaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentResponse> complete3DSPayment(CompleteThreeDSPaymentRequest completeThreeDSPaymentRequest) {
        return craftgateWebClient.post()
                .uri(COMPLETE_3D.path())
                .headers(authorizationHeaderProvider.buildSignature(completeThreeDSPaymentRequest, COMPLETE_3D.path()))
                .bodyValue(completeThreeDSPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentResponse> postAuthPayment(long paymentId, PostAuthPaymentRequest postAuthPaymentRequest) {
        return craftgateWebClient.post()
                .uri(POST_AUTH_PAYMENT.path(), paymentId)
                .headers(authorizationHeaderProvider.buildSignature(postAuthPaymentRequest, POST_AUTH_PAYMENT.path()))
                .bodyValue(postAuthPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<InitCheckoutPaymentResponse> initCheckoutPayment(InitCheckoutPaymentRequest initCheckoutPaymentRequest) {
        return craftgateWebClient.post()
                .uri(INIT_CHECKOUT_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(initCheckoutPaymentRequest, INIT_CHECKOUT_PAYMENT.path()))
                .bodyValue(initCheckoutPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<InitCheckoutPaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentResponse> retrieveCheckoutPayment(String token) {
        return craftgateWebClient.get()
                .uri(RETRIEVE_CHECKOUT_PAYMENT.path(), token)
                .headers(authorizationHeaderProvider.buildSignature(RETRIEVE_CHECKOUT_PAYMENT.path().replace("{token}", token)))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<DepositPaymentResponse> createDepositPayment(CreateDepositPaymentRequest createDepositPaymentRequest) {
        return craftgateWebClient.post()
                .uri(DEPOSIT_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(createDepositPaymentRequest, DEPOSIT_PAYMENT.path()))
                .bodyValue(createDepositPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<DepositPaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<InitThreeDSPaymentResponse> init3DSDepositPayment(CreateDepositPaymentRequest createDepositPaymentRequest) {
        return craftgateWebClient.post()
                .uri(INIT_3D_DEPOSIT.path())
                .headers(authorizationHeaderProvider.buildSignature(createDepositPaymentRequest, INIT_3D_DEPOSIT.path()))
                .bodyValue(createDepositPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<InitThreeDSPaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<DepositPaymentResponse> complete3DSDepositPayment(CompleteThreeDSPaymentRequest completeThreeDSPaymentRequest) {
        return craftgateWebClient.post()
                .uri(COMPLETE_3D_DEPOSIT_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(completeThreeDSPaymentRequest, COMPLETE_3D_DEPOSIT_PAYMENT.path()))
                .bodyValue(completeThreeDSPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<DepositPaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<FundTransferDepositPaymentResponse> createFundTransferDepositPayment(CreateFundTransferDepositPaymentRequest createFundTransferDepositPaymentRequest) {
        return craftgateWebClient.post()
                .uri(CREATE_FUND_TRANSFER_DEPOSITS_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(createFundTransferDepositPaymentRequest, CREATE_FUND_TRANSFER_DEPOSITS_PAYMENT.path()))
                .bodyValue(createFundTransferDepositPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<FundTransferDepositPaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<InitGarantiPayPaymentResponse> initGarantiPayPayment(InitGarantiPayPaymentRequest initGarantiPayPaymentRequest) {
        return craftgateWebClient.post()
                .uri(CREATE_FUND_TRANSFER_DEPOSITS_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(initGarantiPayPaymentRequest, CREATE_FUND_TRANSFER_DEPOSITS_PAYMENT.path()))
                .bodyValue(initGarantiPayPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<InitGarantiPayPaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<ApmPaymentInitResponse> initApmPayment(InitApmPaymentRequest initApmPaymentRequest) {
        return craftgateWebClient.post()
                .uri(INIT_APM_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(initApmPaymentRequest, INIT_APM_PAYMENT.path()))
                .bodyValue(initApmPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<ApmPaymentInitResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<ApmPaymentCompleteResponse> completeApmPayment(CompleteApmPaymentRequest completeApmPaymentRequest) {
        return craftgateWebClient.post()
                .uri(COMPLETE_APM_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(completeApmPaymentRequest, COMPLETE_APM_PAYMENT.path()))
                .bodyValue(completeApmPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<ApmPaymentCompleteResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentResponse> createApmPayment(CreateApmPaymentRequest createApmPaymentRequest) {
        return craftgateWebClient.post()
                .uri(COMPLETE_APM_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(createApmPaymentRequest, COMPLETE_APM_PAYMENT.path()))
                .bodyValue(createApmPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<RetrieveLoyaltiesResponse> retrieveLoyalties(RetrieveLoyaltiesRequest retrieveLoyaltiesRequest) {
        return craftgateWebClient.post()
                .uri(RETRIEVE_LOYALTIES.path())
                .headers(authorizationHeaderProvider.buildSignature(retrieveLoyaltiesRequest, RETRIEVE_LOYALTIES.path()))
                .bodyValue(retrieveLoyaltiesRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<RetrieveLoyaltiesResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentTransactionRefundResponse> refundPaymentTransaction(RefundPaymentTransactionRequest refundPaymentTransactionRequest) {
        return craftgateWebClient.post()
                .uri(REFUND_PAYMENT_TRANSACTION.path())
                .headers(authorizationHeaderProvider.buildSignature(refundPaymentTransactionRequest, REFUND_PAYMENT_TRANSACTION.path()))
                .bodyValue(refundPaymentTransactionRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentTransactionRefundResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentTransactionRefundResponse> retrievePaymentTransactionRefund(Long id) {
        return craftgateWebClient.get()
                .uri(RETRIEVE_REFUND_PAYMENT_TRANSACTION.path(), id)
                .headers(authorizationHeaderProvider.buildSignature(RETRIEVE_REFUND_PAYMENT_TRANSACTION.path()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentTransactionRefundResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentRefundResponse> refundPayment(RefundPaymentRequest refundPaymentRequest) {
        return craftgateWebClient.post()
                .uri(REFUND_PAYMENT.path())
                .headers(authorizationHeaderProvider.buildSignature(refundPaymentRequest, REFUND_PAYMENT.path()))
                .bodyValue(refundPaymentRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentRefundResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentRefundResponse> retrievePaymentRefund(Long id) {
        return craftgateWebClient.get()
                .uri(RETRIEVE_REFUND_PAYMENT.path(), id)
                .headers(authorizationHeaderProvider.buildSignature(RETRIEVE_REFUND_PAYMENT.path()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentRefundResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<StoredCardResponse> storeCard(StoreCardRequest storeCardRequest) {
        return craftgateWebClient.post()
                .uri(STORE_CARD.path())
                .headers(authorizationHeaderProvider.buildSignature(storeCardRequest, STORE_CARD.path()))
                .bodyValue(storeCardRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<StoredCardResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<StoredCardResponse> updateCard(UpdateCardRequest updateCardRequest) {
        return craftgateWebClient.post()
                .uri(UPDATE_CARD.path())
                .headers(authorizationHeaderProvider.buildSignature(updateCardRequest, UPDATE_CARD.path()))
                .bodyValue(updateCardRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<StoredCardResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<StoredCardListResponse> searchStoredCards(SearchStoredCardsRequest searchStoredCardsRequest) {
        String query = SEARCH_CARD.path() + RequestQueryParamsBuilder.buildQueryParam(searchStoredCardsRequest);
        return craftgateWebClient.get()
                .uri(query)
                .headers(authorizationHeaderProvider.buildSignature(query))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<StoredCardListResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public void deleteStoredCard(DeleteStoredCardRequest deleteStoredCardRequest) {
        craftgateWebClient.delete()
                .uri(DELETE_CARD.path(), RequestQueryParamsBuilder.buildQueryParam(deleteStoredCardRequest))
                .headers(authorizationHeaderProvider.buildSignature(DELETE_CARD.path()))
                .retrieve();
    }

    @Override
    public Mono<PaymentTransactionApprovalListResponse> approvePaymentTransactions(ApprovePaymentTransactionsRequest approvePaymentTransactionsRequest) {
        return craftgateWebClient.post()
                .uri(APPROVE_PAYMENT_TRANSACTION.path())
                .headers(authorizationHeaderProvider.buildSignature(approvePaymentTransactionsRequest, APPROVE_PAYMENT_TRANSACTION.path()))
                .bodyValue(approvePaymentTransactionsRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentTransactionApprovalListResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentTransactionApprovalListResponse> disapprovePaymentTransactions(DisapprovePaymentTransactionsRequest disapprovePaymentTransactionsRequest) {
        return craftgateWebClient.post()
                .uri(DISAPPROVE_PAYMENT_TRANSACTION.path())
                .headers(authorizationHeaderProvider.buildSignature(disapprovePaymentTransactionsRequest, APPROVE_PAYMENT_TRANSACTION.path()))
                .bodyValue(disapprovePaymentTransactionsRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentTransactionApprovalListResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<CheckMasterpassUserResponse> checkMasterPassUser(CheckMasterpassUserRequest checkMasterpassUserRequest) {
        return craftgateWebClient.post()
                .uri(CHECK_MASTER_PASS_USER.path())
                .headers(authorizationHeaderProvider.buildSignature(checkMasterpassUserRequest, CHECK_MASTER_PASS_USER.path()))
                .bodyValue(checkMasterpassUserRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<CheckMasterpassUserResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<PaymentTransactionResponse> updatePaymentTransaction(UpdatePaymentTransactionRequest updatePaymentTransactionRequest) {
        return craftgateWebClient.put()
                .uri(UPDATE_PAYMENT_TRANSACTION.path(), updatePaymentTransactionRequest.getPaymentTransactionId())
                .headers(authorizationHeaderProvider.buildSignature(updatePaymentTransactionRequest, UPDATE_PAYMENT_TRANSACTION.path()))
                .bodyValue(updatePaymentTransactionRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<PaymentTransactionResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

}
