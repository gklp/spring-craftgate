package io.github.gklp.springcraftgate.adapters;

import io.craftgate.request.SearchInstallmentsRequest;
import io.craftgate.response.BinNumberResponse;
import io.craftgate.response.InstallmentListResponse;
import io.github.gklp.springcraftgate.QueryUtil;
import io.github.gklp.springcraftgate.provider.AuthorizationHeaderProvider;
import io.github.gklp.springcraftgate.support.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static io.github.gklp.springcraftgate.Constants.InstallmentPaths.RETRIEVE_BIN_NUMBER;
import static io.github.gklp.springcraftgate.Constants.InstallmentPaths.SEARCH_INSTALLMENTS;

@Component
@RequiredArgsConstructor
public class ReactiveCraftGateInstallment implements CraftGateInstallment {

    private final WebClient craftgateWebClient;

    private final AuthorizationHeaderProvider authorizationHeaderProvider;

    @Override
    public Mono<InstallmentListResponse> searchInstallments(SearchInstallmentsRequest searchInstallmentsRequest) {
        String query = QueryUtil.buildQueryParam(searchInstallmentsRequest);
        return craftgateWebClient.get()
                .uri(SEARCH_INSTALLMENTS.path().concat(query))
                .headers(authorizationHeaderProvider.buildSignature(SEARCH_INSTALLMENTS.path().concat(query)))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<InstallmentListResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<BinNumberResponse> retrieveBinNumber(String binNumber) {
        return craftgateWebClient.get()
                .uri(RETRIEVE_BIN_NUMBER.path(), binNumber)
                .headers(authorizationHeaderProvider.buildSignature(RETRIEVE_BIN_NUMBER.path().replace("{binNumber}", binNumber)))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<BinNumberResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }
}
