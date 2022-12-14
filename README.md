# Getting Started

[![Foresight Docs](https://api-public.service.runforesight.com/api/v1/badge/success?repoId=ac4b46b0-7c39-42e1-9b4e-5308490f475c)](https://docs.runforesight.com/)
[![Foresight Docs](https://api-public.service.runforesight.com/api/v1/badge/test?repoId=ac4b46b0-7c39-42e1-9b4e-5308490f475c)](https://docs.runforesight.com/)
[![Foresight Docs](https://api-public.service.runforesight.com/api/v1/badge/utilization?repoId=ac4b46b0-7c39-42e1-9b4e-5308490f475c)](https://docs.runforesight.com/)

### Craftgate Payment Gateway Spring Supported Library

You can add craftgate-spring as a dependency and then use **plug-and-play** for payment.

maven

```
<dependency>
    <groupId>io.gklp.craftgate</groupId>
    <artifactId>spring-craftgate</artifactId>
    <version>1.0.0</version>
</dependency>
```

gradle

```
implementation 'io.gklp.craftgate:spring-craftgate:1.0.0'
```

### Configurations

You can get required information using https://sandbox-panel.craftgate.io/login

```yaml
craftgate:
  api:
    url: https://sandbox-api.craftgate.io
    key: <your_key>
    secret: <your_secret>
    authVersion: v1
    clientVersion: craftgate-java-client:1.0.33
  log:
    enabled: false
```

Configuration defaults. Intelligent code completion gives advice for the completion of the spring application YAML. They
are already defined by the library.

| property                    | defaults                     |
|-----------------------------|------------------------------|
| craftgate.enabled           | true                         |
| craftgate.api.url           | n/a                          |
| craftgate.api.key           | n/a                          |
| craftgate.api.secret        | n/a                          |
| craftgate.api.authVersion   | v1                           |
| craftgate.api.clientVersion | craftgate-java-client:1.0.33 |
| craftgate.log.enabled       | false                        |

**Minimal Configuration**

```yaml
craftgate:
  api:
    url: https://sandbox-api.craftgate.io
    key: <your_key>
    secret: <your_secret>
```

* [Offical Developer Guide](https://developer.craftgate.io/)

### Simple Usage

**Reactive Way**

```java

@Service
public class MyPaymentService {

    @Autowired
    private CraftgatePayment payment;

    public void makePayment() {
        CreatePaymentRequest request = //<ommited>;

        Mono<PaymentResponse> payment = underTest.createPayment(request);
    }
}
```

**Non-Reactive Way**

```java

@Service
public class MyPaymentService {

    @Autowired
    private CraftgatePayment payment;

    public void makePayment() {
        CreatePaymentRequest request = //<ommited>;

        PaymentResponse response = underTest.createPayment(request).block();
    }
}
```

* [Test Cards](https://developer.craftgate.io/test-kartlari/)

### Additional Information

**Exception Handling**

You can handle exceptions using CraftgateException class. This class provides pretty descriptions for you.

* [Offical Error Codes](https://developer.craftgate.io/hata-gruplari)

**Signature Creation**

If you want to call Craftgate API, you need to pass the signature encoded with header. This signature creation process
requires a key. As default you can use below definition.
Otherwise, you can define a bean called craftgateRandomKeyProvider with a different implementation.

```java
@Bean
@ConditionalOnMissingBean
public RandomKeyProvider craftgateRandomKeyProvider() {
    return ()->UUID.randomUUID().toString();
}
```

**Custom object mapper**

You can change object mapper properties and provide new encoder or decoder like below beans.

```java
@Bean
@ConditionalOnMissingBean
public ExchangeStrategies.Builder craftgateExchangeBuilder() {
    ObjectMapper craftgateObjectMapper = buildCustomObjectMapper();
    Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(craftgateObjectMapper, MediaType.APPLICATION_JSON);
    Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(craftgateObjectMapper, MediaType.APPLICATION_JSON);

    return ExchangeStrategies.builder().codecs(configurer -> {
    configurer.defaultCodecs().jackson2JsonEncoder(encoder);
    configurer.defaultCodecs().jackson2JsonDecoder(decoder);
  });
}
```