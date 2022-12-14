package io.github.gklp.springcraftgate.provider;

import io.github.gklp.springcraftgate.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class AuthorizationHeaderProvider implements AuthorizationProvider {

    private final RandomKeyProvider craftgateRandomKeyProvider;

    private final HashGenerator hashGenerator;

    @Override
    public Consumer<HttpHeaders> buildSignature(Object request, String path) {
        String randomKey = craftgateRandomKeyProvider.generate();
        return httpHeaders -> {
            httpHeaders.add(Constants.CraftgateHeaders.X_RND_KEY.getHeaderKey(), randomKey);
            httpHeaders.add(Constants.CraftgateHeaders.X_SIGNATURE.getHeaderKey(), buildSignature(request, path, randomKey));
        };
    }

    @Override
    public Consumer<HttpHeaders> buildSignature(String path) {
        return buildSignature(null, path);
    }

    private String buildSignature(Object request, String path, String randomKey) {
        return hashGenerator.generateHash(randomKey, request, path);
    }

}

