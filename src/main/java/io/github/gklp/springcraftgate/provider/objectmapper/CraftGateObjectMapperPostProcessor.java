package io.github.gklp.springcraftgate.provider.objectmapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.craftgate.model.Loyalty;
import io.craftgate.model.Reward;
import io.github.gklp.springcraftgate.provider.annotation.CraftGateObjectMapper;
import io.github.gklp.springcraftgate.provider.objectmapper.mixin.LoyaltyMixIn;
import io.github.gklp.springcraftgate.provider.objectmapper.mixin.RewardMixIn;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * The CraftGate backend doesn't want null values in the request. Therefore, I need to create a custom ObjectMapper.
 * While doing this, I don't want to make manipulation of Spring default ObjectMapper. It might be caused enormous side effects for user's application.
 * <p>
 * However, the Spring Framework marks ObjectMapper bean as @ConditionalOnMissingBean. For this reason, we cannot handle it over the application
 * context easily. So if we want to add a custom ObjectMapper, we need to manage it in the bean creation lifecycle.
 * </p>
 *
 * @author Gokalp Kuscu
 * @see CraftGateObjectMapper
 * @since 1.0.0
 */
@Component
public class CraftGateObjectMapperPostProcessor implements BeanFactoryPostProcessor {

    public static final String CRAFTGATE_OBJECT_MAPPER_BEAN_NAME = "craftGateObjectMapper";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        final AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(ObjectMapper.class, this::craftgateObjectMapper)
                .setPrimary(false)
                .getBeanDefinition();

        beanDefinition.addQualifier(new AutowireCandidateQualifier(CraftGateObjectMapper.class));
        ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(CRAFTGATE_OBJECT_MAPPER_BEAN_NAME, beanDefinition);
    }

    private ObjectMapper craftgateObjectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .addMixIn(Loyalty.class, LoyaltyMixIn.class)
                .addMixIn(Reward.class, RewardMixIn.class)
                .build();
    }
}
