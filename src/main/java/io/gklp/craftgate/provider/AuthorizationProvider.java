package io.gklp.craftgate.provider;

import org.springframework.http.HttpHeaders;

import java.util.function.Consumer;

public interface AuthorizationProvider {

    Consumer<HttpHeaders> buildSignature(Object request, String path);

    Consumer<HttpHeaders> buildSignature(String path);
}
