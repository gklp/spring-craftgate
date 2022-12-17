package io.github.gklp.springcraftgate.adapters;

import io.craftgate.request.CreateMemberRequest;
import io.craftgate.request.SearchMembersRequest;
import io.craftgate.request.UpdateMemberRequest;
import io.craftgate.response.MemberListResponse;
import io.craftgate.response.MemberResponse;
import io.github.gklp.springcraftgate.QueryUtil;
import io.github.gklp.springcraftgate.provider.AuthorizationHeaderProvider;
import io.github.gklp.springcraftgate.support.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static io.github.gklp.springcraftgate.Constants.OnboardingPaths.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReactiveCraftGateOnboarding implements CraftGateOnboarding {

    private final WebClient craftgateWebClient;

    private final AuthorizationHeaderProvider authorizationHeaderProvider;

    @Override
    public Mono<MemberResponse> createMember(CreateMemberRequest createMemberRequest) {
        return craftgateWebClient.post()
                .uri(CREATE_MEMBER.path())
                .headers(authorizationHeaderProvider.buildSignature(createMemberRequest, CREATE_MEMBER.path()))
                .bodyValue(createMemberRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<MemberResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<MemberResponse> updateMember(Long id, UpdateMemberRequest createMemberRequest) {
        return craftgateWebClient.put()
                .uri(UPDATE_MEMBER.path(), id)
                .headers(authorizationHeaderProvider.buildSignature(createMemberRequest, UPDATE_MEMBER.path().replace("{id}", id.toString())))
                .bodyValue(createMemberRequest)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<MemberResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<MemberResponse> retrieveMember(Long id) {
        return craftgateWebClient.get()
                .uri(RETRIEVE_MEMBER.path(), id)
                .headers(authorizationHeaderProvider.buildSignature(RETRIEVE_MEMBER.path().replace("{id}", id.toString())))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<MemberResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

    @Override
    public Mono<MemberListResponse> searchMembers(SearchMembersRequest searchMembersRequest) {
        String query = QueryUtil.buildQueryParam(searchMembersRequest);
        return craftgateWebClient.get()
                .uri(SEARCH_MEMBERS.path().concat(query))
                .headers(authorizationHeaderProvider.buildSignature(SEARCH_MEMBERS.path().concat(query)))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<MemberListResponse>>() {
                })
                .mapNotNull(ApiResponse::getData);
    }

}
