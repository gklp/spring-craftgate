package io.github.gklp.springcraftgate.adapters;

import io.craftgate.request.SearchInstallmentsRequest;
import io.craftgate.response.BinNumberResponse;
import io.craftgate.response.InstallmentListResponse;
import reactor.core.publisher.Mono;

public interface CraftGateInstallment {

    Mono<InstallmentListResponse> searchInstallments(SearchInstallmentsRequest searchInstallmentsRequest);

    Mono<BinNumberResponse> retrieveBinNumber(String binNumber);

}
