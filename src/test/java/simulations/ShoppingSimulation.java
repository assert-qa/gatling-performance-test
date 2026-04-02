package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import config.Protocols;
import scenarios.ShoppingScenario;

import static io.gatling.javaapi.core.CoreDsl.*;

/**
 * Shopping Simulation for Restful Booker API
 * Tests complete booking workflow (CRUD operations)
 * 
 * Run with: mvn gatling:test -Dgatling.simulationClass=simulations.ShoppingSimulation
 */
public class ShoppingSimulation extends Simulation {

    ScenarioBuilder scn = ShoppingScenario.completeBookingJourney();

    {
        setUp(
            scn.injectOpen(
                rampUsers(15).during(ofSeconds(30))
            )
        )
        .protocols(Protocols.HTTP_PROTOCOL)
        .assertions(
            global().responseTime().max().lt(5000),
            global().responseTime().percentile95().lt(3000),
            global().successfulRequests().percent().gte(95.0)
        );
    }
}

