package requests;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

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
            .check(jsonPath("[*].bookingid").exists())
            .check(jsonPath("[]").count().saveAs("bookingCount"))
            .check(jsonPath("[*].bookingid").findAll().saveAs("bookings")));
    }

    /**
     * Get booking by ID
     * GET /booking/{id}
     * Uses bookingId from createBooking()
     * Does NOT require authentication
     */
    public static ChainBuilder getBookingById() {
        return exec(http("Get Booking By ID")
            .get("/booking/#{bookingId}")
            .check(status().is(200))
            .check(jsonPath("$.firstname").exists())
            .check(jsonPath("$.lastname").exists()));
    }

    /**
     * Create new booking
     * POST /booking
     * Body: booking details
     * Response: { "bookingid": <id>, "booking": {...} }
     */
    public static ChainBuilder createBooking() {
        return exec(http("Create Booking")
            .post("/booking")
            .header("Content-Type", "application/json")
            .body(StringBody(
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
            ))
            .check(status().is(200))
            .check(substring("bookingid"))
            .check(jsonPath("$.bookingid").ofInt().saveAs("bookingId")));
    }

    /**
     * Update booking (requires token as Cookie)
     * PUT /booking/{id}
     * Token passed in Cookie header from createToken()
     */
    public static ChainBuilder updateBooking() {
        return exec(http("Update Booking")
            .put("/booking/#{bookingId}")
            .header("Content-Type", "application/json")
            .header("Cookie", "token=#{authToken}")
            .body(StringBody(
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
            ))
            .check(status().is(200)));
    }

    /**
     * Delete booking (requires token as Cookie)
     * DELETE /booking/{id}
     * Token passed in Cookie header from createToken()
     * Returns status 201 with body "Created"
     */
    public static ChainBuilder deleteBooking() {
        return exec(http("Delete Booking")
            .delete("/booking/#{bookingId}")
            .header("Cookie", "token=#{authToken}")
            .check(status().is(201)));
    }

    /**
     * Complete booking flow: Create > Read > Update > Delete
     */
    public static ChainBuilder completeBookingFlow() {
        return createBooking()
            .pause(1)
            .exec(getBookingById())
            .pause(1)
            .exec(updateBooking())
            .pause(1)
            .exec(deleteBooking());
    }
}

