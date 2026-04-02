package requests;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

/**
 * Authentication Requests for Restful Booker API
 * Handles token creation for API access
 */
public class AuthRequests {

    /**
     * Create authentication token
     * POST /auth
     * Body: {"username": "admin", "password": "password123"}
     */
    public static ChainBuilder createToken() {
        return exec(http("Create Token")
            .post("/auth")
            .body(StringBody("{\"username\":\"admin\",\"password\":\"password123\"}"))
            .check(status().is(200))
            .check(jsonPath("$.token").exists())
            .check(jsonPath("$.token").saveAs("authToken")));
    }

    /**
     * Validate token creation and extract for use in other requests
     */
    public static ChainBuilder createTokenAndValidate() {
        return createToken()
            .exec(session -> {
                String token = session.getString("authToken");
                System.out.println("Token created: " + token);
                return session;
            });
    }
}

