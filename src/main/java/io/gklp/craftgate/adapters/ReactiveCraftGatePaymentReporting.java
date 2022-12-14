package io.gklp.craftgate.adapters;

import io.craftgate.request.SearchPaymentsRequest;
import io.craftgate.response.ReportingPaymentListResponse;
import io.gklp.craftgate.Constants;
import io.gklp.craftgate.QueryUtil;
import io.gklp.craftgate.provider.AuthorizationHeaderProvider;
import io.gklp.craftgate.support.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReactiveCraftGatePaymentReporting implements CraftGatePaymentReporting {

    private final WebClient craftgateWebClient;

    private final AuthorizationHeaderProvider authorizationHeaderProvider;

    @Override
    public Mono<ReportingPaymentListResponse> searchPayments(SearchPaymentsRequest searchPaymentsRequest) {
        String query = QueryUtil.buildQueryParam(searchPaymentsRequest);
        return craftgateWebClient.get()
                .uri(Constants.PaymentReportingPaths.SEARCH_PAYMENTS.path().concat(query))
                .headers(authorizationHeaderProvider.buildSignature(Constants.PaymentReportingPaths.SEARCH_PAYMENTS.path().concat(query)))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<ReportingPaymentListResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }
}
