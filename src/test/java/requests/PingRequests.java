package requests;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Ping Requests for Restful Booker API
 * Health check and availability verification
 */
public class PingRequests {

    /**
     * Ping health check endpoint
     * GET /ping
     * Returns 201 Created if API is healthy
     */
    public static ChainBuilder ping() {
        return exec(http("Ping Health Check")
            .get("/ping")
            .check(status().is(201)));
    }

    /**
     * Repeated ping for availability monitoring
     */
    public static ChainBuilder repeatPing() {
        return ping()
            .pause(1)
            .ping()
            .pause(1)
            .ping();
    }
}

