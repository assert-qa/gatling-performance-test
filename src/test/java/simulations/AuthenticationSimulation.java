package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import config.Protocols;
import scenarios.AuthenticationScenario;

import static io.gatling.javaapi.core.CoreDsl.*;

/**
 * Authentication Simulation for Restful Booker API
 * Tests token generation performance
 * 
 * Run with: mvn gatling:test -Dgatling.simulationClass=simulations.AuthenticationSimulation
 */
public class AuthenticationSimulation extends Simulation {

    ScenarioBuilder scn = AuthenticationScenario.authenticationFlow();

    {
        setUp(
            scn.injectOpen(
                rampUsers(10).during(ofSeconds(30))
            )
        )
        .protocols(Protocols.HTTP_PROTOCOL)
        .assertions(
            global().responseTime().max().lt(5000),
            global().successfulRequests().percent().gte(95.0)
        );
    }
}

