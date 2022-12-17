package io.github.gklp.springcraftgate.adapters;

import io.craftgate.request.CreateMemberRequest;
import io.craftgate.request.SearchMembersRequest;
import io.craftgate.request.UpdateMemberRequest;
import io.craftgate.response.MemberListResponse;
import io.craftgate.response.MemberResponse;
import reactor.core.publisher.Mono;

public interface CraftGateOnboarding {

    Mono<MemberResponse> createMember(CreateMemberRequest createMemberRequest);

    Mono<MemberResponse> updateMember(Long id, UpdateMemberRequest createMemberRequest);

    Mono<MemberResponse> retrieveMember(Long id);

    Mono<MemberListResponse> searchMembers(SearchMembersRequest searchMembersRequest);

}
