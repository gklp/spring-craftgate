package io.github.gklp.springcraftgate.adapters;

import io.craftgate.request.CreateMemberRequest;
import io.craftgate.request.SearchMembersRequest;
import io.craftgate.request.UpdateMemberRequest;
import io.craftgate.response.MemberListResponse;
import io.craftgate.response.MemberResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.OnboardingTestData;
import utils.TestConfiguration;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
public class ReactiveCraftGateOnboardingTests {

    @Autowired
    private CraftGateOnboarding underTest;

    @Test
    void should_create_sub_merchant() {
        //Given
        CreateMemberRequest request = OnboardingTestData.memberRequest();

        //When
        MemberResponse response = underTest.createMember(request).block();

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(request.getContactName()).isEqualTo(response.getContactName());
        assertThat(request.getContactSurname()).isEqualTo(response.getContactSurname());
        assertThat(request.getEmail()).isEqualTo(response.getEmail());
        assertThat(request.getPhoneNumber()).isEqualTo(response.getPhoneNumber());
        assertThat(request.getIban()).isEqualTo(response.getIban());
        assertThat(request.getIdentityNumber()).isEqualTo(response.getIdentityNumber());
        assertThat(request.getLegalCompanyTitle()).isEqualTo(response.getLegalCompanyTitle());
        assertThat(request.getName()).isEqualTo(response.getName());
        assertThat(request.getMemberType()).isEqualTo(response.getMemberType());
        assertThat(request.getMemberExternalId()).isEqualTo(response.getMemberExternalId());
        assertThat(request.getTaxNumber()).isEqualTo(response.getTaxNumber());
        assertThat(request.getTaxOffice()).isEqualTo(response.getTaxOffice());
        assertThat(request.getAddress()).isEqualTo(response.getAddress());
    }

    @Test
    void should_update_sub_merchant() {
        //Given
        CreateMemberRequest createMemberRequest = OnboardingTestData.memberRequest();
        MemberResponse memberCreationResponse = underTest.createMember(createMemberRequest).block();
        UpdateMemberRequest request = OnboardingTestData.updateMemberRequest();

        //When
        MemberResponse response = underTest.updateMember(memberCreationResponse.getId(), request).block();

        //Then
        assertThat(response).isNotNull();
        // assertThat(response.getUpdatedDate()).isNotNull();
        assertThat(memberCreationResponse.getId()).isEqualTo(response.getId());
        assertThat(request.getContactName()).isEqualTo(response.getContactName());
        assertThat(request.getContactSurname()).isEqualTo(response.getContactSurname());
        assertThat(request.getEmail()).isEqualTo(response.getEmail());
        assertThat(request.getPhoneNumber()).isEqualTo(response.getPhoneNumber());
        // assertThat(request.getIban()).isEqualTo(response.getIban());
        assertThat(request.getIdentityNumber()).isEqualTo(response.getIdentityNumber());
        assertThat(request.getLegalCompanyTitle()).isEqualTo(response.getLegalCompanyTitle());
        assertThat(request.getName()).isEqualTo(response.getName());
        assertThat(request.getTaxNumber()).isEqualTo(response.getTaxNumber());
        assertThat(request.getTaxOffice()).isEqualTo(response.getTaxOffice());
        assertThat(request.getAddress()).isEqualTo(response.getAddress());
    }

    @Test
    void should_retrieve_sub_merchant() {
        //Given
        CreateMemberRequest createMemberRequest = OnboardingTestData.memberRequest();
        MemberResponse memberCreationResponse = underTest.createMember(createMemberRequest).block();

        //When
        assert memberCreationResponse != null;
        MemberResponse response = underTest.retrieveMember(memberCreationResponse.getId()).block();

        //Then
        assertThat(response).isNotNull();
        assertThat(memberCreationResponse.getId()).isEqualTo(response.getId());
    }

    @Test
    void should_create_buyer_member() {
        //Given
        CreateMemberRequest request = OnboardingTestData.buyerMemberRequest();

        //When
        MemberResponse response = underTest.createMember(request).block();

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getIsBuyer()).isTrue();
        assertThat(request.getMemberExternalId()).isEqualTo(response.getMemberExternalId());
        assertThat(request.getEmail()).isEqualTo(response.getEmail());
        assertThat(request.getPhoneNumber()).isEqualTo(response.getPhoneNumber());
        assertThat(request.getName()).isEqualTo(response.getName());
        assertThat(request.getIdentityNumber()).isEqualTo(response.getIdentityNumber());
        assertThat(request.getNegativeWalletAmountLimit()).isEqualTo(response.getNegativeWalletAmountLimit());
    }

    @Test
    void should_update_buyer_member() {
        //Given
        MemberResponse memberResponse = underTest.createMember(OnboardingTestData.buyerMemberRequest()).block();
        assert memberResponse != null;
        Long memberId = memberResponse.getId();

        UpdateMemberRequest request = OnboardingTestData.updateBuyerMemberRequest();

        //When
        MemberResponse response = underTest.updateMember(memberId, request).block();

        assertThat(response).isNotNull();
        assertThat(response.getIsBuyer()).isTrue();
        assertThat(memberId).isEqualTo(response.getId());
        assertThat(request.getEmail()).isEqualTo(response.getEmail());
        assertThat(request.getPhoneNumber()).isEqualTo(response.getPhoneNumber());
        assertThat(request.getName()).isEqualTo(response.getName());
        assertThat(request.getNegativeWalletAmountLimit()).isEqualTo(response.getNegativeWalletAmountLimit());
    }

    @Test
    void should_retrieve_buyer() {
        //Given
        MemberResponse memberResponse = underTest.createMember(OnboardingTestData.buyerMemberRequest()).block();
        assert memberResponse != null;
        Long memberId = memberResponse.getId();

        //When
        MemberResponse response = underTest.retrieveMember(memberId).block();

        //Then
        assertThat(response).isNotNull();
        assertThat(memberId).isEqualTo(response.getId());
    }

    @Test
    void should_search_buyer_member() {
        //Given
        MemberResponse memberResponse1 = underTest.createMember(OnboardingTestData.buyerMemberRequest()).block();
        MemberResponse memberResponse2 = underTest.createMember(OnboardingTestData.buyerMemberRequest()).block();

        assert memberResponse1 != null;
        assert memberResponse2 != null;

        SearchMembersRequest request = OnboardingTestData.searchMembersRequest(new HashSet<Long>() {{
            add(memberResponse1.getId());
            add(memberResponse2.getId());
        }});

        //When
        MemberListResponse response = underTest.searchMembers(request).block();

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotNull();
        assertThat(response.getItems().size()).isEqualTo(2);
    }

}
