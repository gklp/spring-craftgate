package io.github.gklp.springcraftgate.config.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public abstract class RewardMixIn {

    @JsonCreator
    public RewardMixIn(@JsonProperty("cardRewardMoney") BigDecimal cardRewardMoney,
                       @JsonProperty("firmRewardMoney") BigDecimal firmRewardMoney) {
    }
}
