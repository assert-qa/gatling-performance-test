package scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import requests.BookingRequests;
import requests.PingRequests;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

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
            .pause(1)
            // Extract first bookingid from list response for subsequent getBookingById calls
            .exec(session -> {
                // Get the booking list and extract the first bookingid
                java.util.List<Object> bookings = session.getList("bookings");
                if (bookings != null && !bookings.isEmpty()) {
                    Object firstBooking = bookings.get(0);
                    Integer bookingId = firstBooking instanceof Integer ? 
                        (Integer) firstBooking : 
                        Integer.parseInt(firstBooking.toString());
                    return session.set("bookingId", bookingId);
                }
                return session.set("bookingId", 1); // fallback
            })
            .pause(1)
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





