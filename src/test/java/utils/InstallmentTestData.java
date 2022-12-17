package utils;

import io.craftgate.model.Currency;
import io.craftgate.request.SearchInstallmentsRequest;

import java.math.BigDecimal;

public final class InstallmentTestData {

    private InstallmentTestData() {
    }

    public static String BIN_NUMBER = "525864";

    public static SearchInstallmentsRequest searchInstallmentsRequest(boolean distinctCardBrandsWithLowestCommissions) {
        return SearchInstallmentsRequest.builder()
                .binNumber(BIN_NUMBER)
                .price(BigDecimal.valueOf(100L))
                .currency(Currency.TRY)
                .distinctCardBrandsWithLowestCommissions(distinctCardBrandsWithLowestCommissions)
                .build();
    }

}
