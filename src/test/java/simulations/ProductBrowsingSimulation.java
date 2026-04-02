package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import config.Protocols;
import scenarios.BrowsingScenario;

import static io.gatling.javaapi.core.CoreDsl.*;

/**
 * Product Browsing Simulation for Restful Booker API
 * Tests booking list retrieval and read operations
 * 
 * Run with: mvn gatling:test -Dgatling.simulationClass=simulations.ProductBrowsingSimulation
 */
public class ProductBrowsingSimulation extends Simulation {

    ScenarioBuilder scn = BrowsingScenario.bookingBrowsing();

    {
        setUp(
            scn.injectOpen(
                rampUsers(20).during(ofSeconds(30))
            )
        )
        .protocols(Protocols.HTTP_PROTOCOL)
        .assertions(
            global().responseTime().max().lt(3000),
            global().responseTime().percentile95().lt(2000),
            global().successfulRequests().percent().gte(98.0)
        );
    }
}

