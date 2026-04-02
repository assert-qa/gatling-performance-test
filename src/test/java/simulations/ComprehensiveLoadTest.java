package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import config.Protocols;
import scenarios.AuthenticationScenario;
import scenarios.BrowsingScenario;
import scenarios.ShoppingScenario;

import static io.gatling.javaapi.core.CoreDsl.*;

/**
 * Comprehensive Load Test for Restful Booker API
 * Tests all three flows: Auth > Booking > Ping
 * Simulates realistic mixed user behavior
 * 
 * Run with: mvn gatling:test -Dgatling.simulationClass=simulations.ComprehensiveLoadTest
 */
public class ComprehensiveLoadTest extends Simulation {

    // Define scenarios
    ScenarioBuilder authFlow = AuthenticationScenario.authenticationFlow();
    ScenarioBuilder browsingFlow = BrowsingScenario.bookingBrowsing();
    ScenarioBuilder shoppingFlow = ShoppingScenario.completeBookingJourney();

    {
        setUp(
            // 30% of users doing authentication
            authFlow.injectOpen(
                rampUsers(10).during(ofSeconds(30))
            ),
            // 40% of users browsing bookings
            browsingFlow.injectOpen(
                rampUsers(15).during(ofSeconds(30))
            ),
            // 30% of users doing complete booking workflow
            shoppingFlow.injectOpen(
                rampUsers(10).during(ofSeconds(30))
            )
        )
        .protocols(Protocols.HTTP_PROTOCOL)
        .assertions(
            // Overall response time assertions
            global().responseTime().max().lt(5000),
            global().responseTime().percentile95().lt(3000),
            global().responseTime().percentile99().lt(4000),
            // Success rate assertions
            global().successfulRequests().percent().gte(95.0),
            // Per-request type assertions
            forAll().responseTime().percentile95().lt(3000)
        );
    }
}

