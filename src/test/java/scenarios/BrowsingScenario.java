package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import requests.BookingRequests;
import requests.PingRequests;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.pause;

/**
 * Booking Browsing Scenario for Restful Booker API
 * Tests read operations on bookings
 */
public class BrowsingScenario {

    public static ScenarioBuilder bookingBrowsing() {
        return scenario("Booking Browsing")
            .exec(PingRequests.ping())
            .pause(1)
            .exec(BookingRequests.getBookingList())
            .pause(2)
            .repeat(3).on(
                BookingRequests.getBookingById()
                    .pause(1)
            );
    }

    /**
     * Extended browsing with multiple pagination loads
     */
    public static ScenarioBuilder extendedBrowsing() {
        return scenario("Extended Booking Browsing")
            .exec(BookingRequests.getBookingList())
            .pause(2)
            .repeat(5).on(
                BookingRequests.getBookingList()
                    .pause(1)
            );
    }
}

