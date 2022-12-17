package io.github.gklp.springcraftgate.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.craftgate.exception.CraftgateException;
import io.github.gklp.springcraftgate.config.properties.CraftGateConfigurationProperties;
import io.github.gklp.springcraftgate.provider.annotation.CraftGateObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class HashGenerator {

    @CraftGateObjectMapper
    private final ObjectMapper objectMapper;

    private final CraftGateConfigurationProperties craftgateConfigurationProperties;

    public String generateHash(String randomString, Object request, String path) {
        try {
            String hashData;
            String apiKey = craftgateConfigurationProperties.getKey().trim();
            String secretKey = craftgateConfigurationProperties.getSecret().trim();
            String decodedUrl = URLDecoder.decode(craftgateConfigurationProperties.getUrl() + path.trim(), StandardCharsets.UTF_8.toString());

            if (request != null) {
                String requestBody = objectMapper.writeValueAsString(request);
                hashData = decodedUrl + apiKey + secretKey + randomString.trim() + requestBody;
            } else {
                hashData = decodedUrl + apiKey + secretKey + randomString.trim();
            }

            return Base64.encodeBase64String(DigestUtils.sha256(hashData));
        } catch (Exception e) {
            throw new CraftgateException(e);
        }
    }

}
