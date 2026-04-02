package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import requests.AuthRequests;
import requests.BookingRequests;
import requests.PingRequests;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.pause;

/**
 * Authentication Scenario for Restful Booker API
 * Tests token generation and validation
 */
public class AuthenticationScenario {

    public static ScenarioBuilder authenticationFlow() {
        return scenario("Authentication Flow")
            .exec(PingRequests.ping())
            .pause(1)
            .exec(AuthRequests.createTokenAndValidate())
            .pause(2);
    }

    /**
     * Repeated authentication for load testing
     */
    public static ScenarioBuilder repeatedAuthentication() {
        return scenario("Repeated Authentication")
            .repeat(5).on(
                AuthRequests.createToken()
                    .pause(1)
            );
    }
}

