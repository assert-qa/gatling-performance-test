package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import requests.AuthRequests;
import requests.BookingRequests;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.pause;

/**
 * Shopping Scenario for Restful Booker API
 * Tests complete booking workflow: Create > Read > Update > Delete
 */
public class ShoppingScenario {

    public static ScenarioBuilder completeBookingJourney() {
        return scenario("Complete Booking Journey")
            // 1. Authenticate
            .exec(AuthRequests.createToken())
            .pause(1)
            // 2. Create new booking
            .exec(BookingRequests.createBooking())
            .pause(2)
            // 3. Read booking
            .exec(BookingRequests.getBookingById())
            .pause(1)
            // 4. Update booking
            .exec(BookingRequests.updateBooking())
            .pause(1)
            // 5. Delete booking
            .exec(BookingRequests.deleteBooking())
            .pause(1);
    }

    /**
     * Multiple sequential bookings - simulate real user behavior
     */
    public static ScenarioBuilder multipleBookings() {
        return scenario("Multiple Bookings")
            .exec(AuthRequests.createToken())
            .pause(1)
            .repeat(3).on(
                BookingRequests.createBooking()
                    .pause(1)
                    .exec(BookingRequests.getBookingById())
                    .pause(1)
            );
    }
}

