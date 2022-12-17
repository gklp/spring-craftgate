package io.github.gklp.springcraftgate.provider.objectmapper.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.craftgate.model.LoyaltyType;
import io.craftgate.model.Reward;

public abstract class LoyaltyMixIn {

    @JsonCreator
    public LoyaltyMixIn(@JsonProperty("type") LoyaltyType type,
                        @JsonProperty("reward") Reward reward,
                        @JsonProperty("message") String message) {
    }
}
