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

    public static String MEMBER_FULL_NAME = "Haluk Demir";
    public static String MEMBER_NAME = "Haluk";
    public static String MEMBER_SURNAME = "Demir";
    public static String MEMBER_EMAIL = "haluk.demir@example.com";
    public static String PHONE_NUMBER = "905551111111";
    public static String ADDRESS = "Suadiye Mah. Örnek Cd. No:23, 34740 Kadıköy/İstanbul";
    public static String IDENTITY_NUMBER = "11111111110";
    public static String LEGAL_COMPANY_TITLE = "Dem Zeytinyağı Üretim Ltd. Şti.";
    public static String COMPANY_NAME = "Dem Zeytinyağı Üretim Ltd. Şti.";
    public static String TAX_NUMBER = "1111111114";
    public static String TAX_OFFICE = "Erenköy";
    public static String IBAN = "TR930006701000000001111111";

    public static CreateMemberRequest buyerMemberRequest() {
        return CreateMemberRequest.builder()
                .memberExternalId(UUID.randomUUID().toString())
                .name(MEMBER_FULL_NAME)
                .email(MEMBER_EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .address(ADDRESS)
                .contactName(MEMBER_NAME)
                .contactSurname(MEMBER_SURNAME)
                .negativeWalletAmountLimit(BigDecimal.valueOf(-50))
                .build();
    }

    public static CreateMemberRequest memberRequest() {
        return CreateMemberRequest.builder()
                .isBuyer(false)
                .isSubMerchant(true)
                .contactName(MEMBER_NAME)
                .contactSurname(MEMBER_SURNAME)
                .email(MEMBER_EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .iban(IBAN)
                .identityNumber(TAX_NUMBER)
                .legalCompanyTitle(LEGAL_COMPANY_TITLE)
                .name(COMPANY_NAME)
                .memberType(MemberType.LIMITED_OR_JOINT_STOCK_COMPANY)
                .memberExternalId(UUID.randomUUID().toString())
                .taxNumber(TAX_NUMBER)
                .taxOffice(TAX_OFFICE)
                .address(ADDRESS)
                .build();

    }

    public static UpdateMemberRequest updateMemberRequest() {
        return UpdateMemberRequest.builder()
                .isBuyer(false)
                .isSubMerchant(true)
                .contactName(MEMBER_NAME)
                .contactSurname(MEMBER_SURNAME)
                .email(MEMBER_EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .identityNumber(IDENTITY_NUMBER)
                .legalCompanyTitle(LEGAL_COMPANY_TITLE)
                .name(COMPANY_NAME)
                .memberType(MemberType.LIMITED_OR_JOINT_STOCK_COMPANY)
                .taxNumber(TAX_NUMBER)
                .taxOffice(TAX_OFFICE)
                .address(ADDRESS)
                .iban(IBAN)
                .settlementEarningsDestination(SettlementEarningsDestination.IBAN)
                .build();
    }

    public static UpdateMemberRequest updateBuyerMemberRequest() {
        return UpdateMemberRequest.builder()
                .name(MEMBER_FULL_NAME)
                .email(MEMBER_EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .address(ADDRESS)
                .contactName(MEMBER_NAME)
                .contactSurname(MEMBER_SURNAME)
                .negativeWalletAmountLimit(BigDecimal.valueOf(-50))
                .build();
    }

    public static SearchMembersRequest searchMembersRequest(Set<Long> memberIds) {
        return SearchMembersRequest.builder()
                .memberIds(memberIds)
                .name(MEMBER_FULL_NAME)
                .build();
    }

}


