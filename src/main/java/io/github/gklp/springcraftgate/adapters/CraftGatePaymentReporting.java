package io.github.gklp.springcraftgate.adapters;

import io.craftgate.request.SearchPaymentsRequest;
import io.craftgate.response.ReportingPaymentListResponse;
import reactor.core.publisher.Mono;

public interface CraftGatePaymentReporting {

    Mono<ReportingPaymentListResponse> searchPayments(SearchPaymentsRequest searchPaymentsRequest);
}
