package io.github.gklp.springcraftgate.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.craftgate.request.CreateMemberRequest;
import io.github.gklp.springcraftgate.provider.annotation.CraftGateObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.OnboardingTestData;
import utils.TestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
public class CraftGateObjectMapperPostProcessorTests {

    @Autowired
    @CraftGateObjectMapper
    private ObjectMapper craftgateObjectMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void should_return_craftgate_object_mapper() {
        //Then
        assertThat(craftgateObjectMapper).isNotNull();
        assertThat(craftgateObjectMapper.mixInCount()).isEqualTo(2);
        assertThat(craftgateObjectMapper).isInstanceOf(JsonMapper.class);
    }

    @Test
    public void should_return_default_object_mapper() {
        //Then
        assertThat(objectMapper).isNotNull();
        assertThat(objectMapper.mixInCount()).isEqualTo(0);
        assertThat(objectMapper).isInstanceOf(ObjectMapper.class);
    }

    @Test
    public void should_not_contains_null_fields_on_json() {
        //Given
        CreateMemberRequest request = OnboardingTestData.memberRequest();

        //When
        JsonNode jsonNode = craftgateObjectMapper.valueToTree(request);

        //Then
        assertThat(jsonNode).isNotNull();
        assertThat(jsonNode.get("negativeWalletAmountLimit")).isNull();
    }

    @Test
    public void should_contains_null_fields_on_json() {
        //Given
        CreateMemberRequest request = OnboardingTestData.memberRequest();

        //When
        JsonNode jsonNode = objectMapper.valueToTree(request);

        //Then
        assertThat(jsonNode).isNotNull();
        assertThat(jsonNode.get("negativeWalletAmountLimit")).isNotNull();
    }

}
