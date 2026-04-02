                                                   package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import config.Protocols;
import scenarios.ShoppingScenario;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;

/**
 * Stress Test for Restful Booker API
 * Tests system behavior under high load
 * Run with: mvn gatling:test -Dgatling.simulationClass=simulations.StressTest
 */
public class StressTest extends Simulation {

    ScenarioBuilder scn = ShoppingScenario.multipleBookings();

    {
        setUp(
            scn.injectOpen(
                // Ramp up gradually to avoid overwhelming the system
                rampUsers(100).during(Duration.ofSeconds(60)),
                // Hold at peak load
                constantUsersPerSec(10).during(Duration.ofMinutes(2))
            )
        )
        .protocols(Protocols.HTTP_PROTOCOL)
        .assertions(
            global().responseTime().max().lt(10000),
            global().responseTime().percentile(99.0).lt(5000),
            global().successfulRequests().percent().gte(90.0)
        );
    }
}

