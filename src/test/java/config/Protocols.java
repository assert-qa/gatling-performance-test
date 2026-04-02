package config;

import io.gatling.javaapi.http.HttpProtocolBuilder;
import java.time.Duration;

import static io.gatling.javaapi.http.HttpDsl.http;

public class Protocols {
    
    public static final HttpProtocolBuilder HTTP_PROTOCOL = http
        .baseUrl(Environment.BASE_URL)
        .acceptHeader("application/json")
        .contentTypeHeader("application/json")
        .userAgentHeader("Gatling Performance Test");
}

