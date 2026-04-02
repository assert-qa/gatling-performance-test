                                                   package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import config.Protocols;
import requests.PingRequests;

import static io.gatling.javaapi.core.CoreDsl.*;

/**
 * Smoke Test for Restful Booker API
 * Basic health check to verify API is accessible
 * Run with: mvn gatling:test -Dgatling.simulationClass=simulations.SmokeTest
 */
public class SmokeTest extends Simulation {

    ScenarioBuilder scn = scenario("Smoke Test")
        .exec(PingRequests.ping());

    {
        setUp(
            scn.injectOpen(
                atOnceUsers(5)
            )
        )
        .protocols(Protocols.HTTP_PROTOCOL)
        .assertions(
            global().responseTime().max().lt(2000),
            global().successfulRequests().percent().is(100.0)
        );
    }
}

