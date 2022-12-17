package io.github.gklp.springcraftgate.adapters;

import io.craftgate.model.CardAssociation;
import io.craftgate.model.CardType;
import io.craftgate.request.SearchInstallmentsRequest;
import io.craftgate.response.BinNumberResponse;
import io.craftgate.response.InstallmentListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.InstallmentTestData;
import utils.TestConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
public class ReactiveCraftGateInstallmentTests {

    @Autowired
    private CraftGateInstallment underTest;

    @Test
    void should_retrieve_bin() {
        //Given
        String binNumber = InstallmentTestData.BIN_NUMBER;

        //When
        BinNumberResponse response = underTest.retrieveBinNumber(binNumber).block();

        //Then
        assertThat(binNumber).isNotNull();
        assertThat(binNumber).isEqualTo(response.getBinNumber());
        assertThat(CardType.CREDIT_CARD).isEqualTo(response.getCardType());
        assertThat(CardAssociation.MASTER_CARD).isEqualTo(response.getCardAssociation());
        assertThat("World").isEqualTo(response.getCardBrand());
        assertThat("YAPI VE KREDİ BANKASI A.Ş.").isEqualTo(response.getBankName());
        assertThat(67L).isEqualTo(response.getBankCode());
        assertThat(false).isEqualTo(response.getCommercial());
    }

    @Test
    void should_search_installments() {
        //Given
        SearchInstallmentsRequest request = InstallmentTestData.searchInstallmentsRequest(false);

        //When
        InstallmentListResponse response = underTest.searchInstallments(request).block();

        //Then,
        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotNull();
        assertThat(response.getItems().size()).isGreaterThan(0);
    }

    @Test
    void should_search_installments_with_distinct_card_brand_with_lowest_commissions() {
        //Given
        SearchInstallmentsRequest request = InstallmentTestData.searchInstallmentsRequest(true);

        //When
        InstallmentListResponse response = underTest.searchInstallments(request).block();

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getItems()).isNotNull();
        assertThat(response.getItems().size()).isGreaterThan(0);
    }

}
