package requests;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import static io.gatling.javaapi.http.HttpDsl.jsonPath;

/**
 * Booking Requests for Restful Booker API
 * Handles CRUD operations for bookings
 */
public class BookingRequests {

    /**
     * Get all bookings
     * GET /booking
     */
    public static ChainBuilder getBookingList() {
        return exec(http("Get Booking List")
            .get("/booking")
            .check(status().is(200))
            .check(jsonPath("$[*].bookingid").exists())
            .check(jsonPath("$").count().saveAs("bookingCount")));
    }

    /**
     * Get booking by ID
     * GET /booking/{id}
     * Uses bookingid from feeder
     */
    public static ChainBuilder getBookingById() {
        return exec(http("Get Booking By ID")
            .get("/booking/#{bookingId}")
            .header("Authorization", "Bearer #{authToken}")
            .check(status().is(200))
            .check(jsonPath("$.firstname").exists())
            .check(jsonPath("$.lastname").exists()));
    }

    /**
     * Create new booking
     * POST /booking
     * Body: booking details
     */
    public static ChainBuilder createBooking() {
        return exec(http("Create Booking")
            .post("/booking")
            .body(
                io.gatling.javaapi.core.CoreDsl.StringBody(
                    "{" +
                    "\"firstname\":\"John\"," +
                    "\"lastname\":\"Doe\"," +
                    "\"totalprice\":100," +
                    "\"depositpaid\":true," +
                    "\"bookingdates\":{" +
                    "\"checkin\":\"2024-01-01\"," +
                    "\"checkout\":\"2024-01-07\"" +
                    "}," +
                    "\"additionalneeds\":\"Breakfast\"" +
                    "}"
                )
            )
            .check(status().is(200))
            .check(jsonPath("$.bookingid").exists())
            .check(jsonPath("$.bookingid").saveAs("newBookingId")));
    }

    /**
     * Update booking (requires token)
     * PUT /booking/{id}
     */
    public static ChainBuilder updateBooking() {
        return exec(http("Update Booking")
            .put("/booking/#{newBookingId}")
            .header("Authorization", "Bearer #{authToken}")
            .body(
                io.gatling.javaapi.core.CoreDsl.StringBody(
                    "{" +
                    "\"firstname\":\"Jane\"," +
                    "\"lastname\":\"Smith\"," +
                    "\"totalprice\":150," +
                    "\"depositpaid\":true," +
                    "\"bookingdates\":{" +
                    "\"checkin\":\"2024-02-01\"," +
                    "\"checkout\":\"2024-02-07\"" +
                    "}," +
                    "\"additionalneeds\":\"Wifi\"" +
                    "}"
                )
            )
            .check(status().is(200)));
    }

    /**
     * Delete booking (requires token)
     * DELETE /booking/{id}
     */
    public static ChainBuilder deleteBooking() {
        return exec(http("Delete Booking")
            .delete("/booking/#{newBookingId}")
            .header("Authorization", "Bearer #{authToken}")
            .check(status().is(201)));
    }

    /**
     * Complete booking flow: Create > Read > Update > Delete
     */
    public static ChainBuilder completeBookingFlow() {
        return createBooking()
            .pause(1)
            .exec(getBookingById().param("bookingId", "#{newBookingId}"))
            .pause(1)
            .updateBooking()
            .pause(1)
            .deleteBooking();
    }
}

