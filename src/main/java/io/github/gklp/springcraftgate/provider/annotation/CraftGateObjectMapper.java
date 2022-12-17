package io.github.gklp.springcraftgate.provider.annotation;

import io.github.gklp.springcraftgate.provider.objectmapper.CraftGateObjectMapperPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * If you want to inject craftgate custom object mapper. You need to assign this annotation.
 * Because this bean was created using auto wire candidate qualifier.
 *
 * @author Gokalp Kuscu
 * @since 1.0.0
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier(CraftGateObjectMapperPostProcessor.CRAFTGATE_OBJECT_MAPPER_BEAN_NAME)
public @interface CraftGateObjectMapper {
}
