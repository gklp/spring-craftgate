package utils;

import io.craftgate.model.MemberType;
import io.craftgate.model.SettlementEarningsDestination;
import io.craftgate.request.CreateMemberRequest;
import io.craftgate.request.SearchMembersRequest;
import io.craftgate.request.UpdateMemberRequest;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public final class OnboardingTestData {

    private OnboardingTestData() {
    }

    public static CreateMemberRequest buyerMemberRequest() {
        return CreateMemberRequest.builder()
                .memberExternalId(UUID.randomUUID().toString())
                .name("Haluk Demir")
                .email("haluk.demir@example.com")
                .phoneNumber("905551111111")
                .address("Suadiye Mah. Örnek Cd. No:23, 34740 Kadıköy/İstanbul")
                .contactName("Haluk")
                .contactSurname("Demir")
                .negativeWalletAmountLimit(BigDecimal.valueOf(-50))
                .build();
    }

    public static CreateMemberRequest memberRequest() {
        return CreateMemberRequest.builder()
                .isBuyer(false)
                .isSubMerchant(true)
                .contactName("Haluk")
                .contactSurname("Demir")
                .email("haluk.demir@example.com")
                .phoneNumber("905551111111")
                .iban("TR930006701000000001111111")
                .identityNumber("11111111110")
                .legalCompanyTitle("Dem Zeytinyağı Üretim Ltd. Şti.")
                .name("Dem Zeytinyağı Üretim Ltd. Şti.")
                .memberType(MemberType.LIMITED_OR_JOINT_STOCK_COMPANY)
                .memberExternalId(UUID.randomUUID().toString())
                .taxNumber("1111111114")
                .taxOffice("Erenköy")
                .address("Suadiye Mah. Örnek Cd. No:23, 34740 Kadıköy/İstanbul")
                .build();

    }

    public static UpdateMemberRequest updateMemberRequest() {
        return UpdateMemberRequest.builder()
                .isBuyer(false)
                .isSubMerchant(true)
                .contactName("Haluk")
                .contactSurname("Demir")
                .email("haluk.demir@example.com")
                .phoneNumber("905551111111")
                .identityNumber("11111111110")
                .legalCompanyTitle("Dem Zeytinyağı Üretim Ltd. Şti.")
                .name("Dem Zeytinyağı Üretim Ltd. Şti.")
                .memberType(MemberType.LIMITED_OR_JOINT_STOCK_COMPANY)
                .taxNumber("1111111114")
                .taxOffice("Erenköy")
                .address("Suadiye Mah. Örnek Cd. No:23, 34740 Kadıköy/İstanbul")
                .iban("TR930006701000000001111111")
                .settlementEarningsDestination(SettlementEarningsDestination.IBAN)
                .build();
    }

    public static UpdateMemberRequest updateBuyerMemberRequest() {
        return UpdateMemberRequest.builder()
                .name("Haluk Demir")
                .email("haluk.demir@example.com")
                .phoneNumber("905551111111")
                .address("Suadiye Mah. Örnek Cd. No:23, 34740 Kadıköy/İstanbul")
                .contactName("Haluk")
                .contactSurname("Demir")
                .negativeWalletAmountLimit(BigDecimal.valueOf(-50))
                .build();
    }

    public static SearchMembersRequest searchMembersRequest(Set<Long> memberIds) {
        return SearchMembersRequest.builder()
                .memberIds(memberIds)
                .name("Zeytinyağı Üretim")
                .build();
    }

}


