package feeders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * User Feeders for Restful Booker API
 * Provides dynamic test data for scenarios
 */
public class UserFeeders {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Generate a single booking data map
     */
    public static Map<String, Object> generateBookingData(int index) {
        Map<String, Object> map = new HashMap<>();
        LocalDate checkIn = LocalDate.now().plusDays(index);
        LocalDate checkOut = checkIn.plusDays(7);
        
        map.put("firstname", "User" + index);
        map.put("lastname", "Tester" + index);
        map.put("totalprice", 100 + (index * 10));
        map.put("depositpaid", index % 2 == 0);
        map.put("checkin", checkIn.format(DATE_FORMATTER));
        map.put("checkout", checkOut.format(DATE_FORMATTER));
        map.put("additionalneeds", "Breakfast" + (index % 2 == 0 ? " and WiFi" : ""));
        
        return map;
    }

    /**
     * Generate test user credentials
     */
    public static Map<String, Object> generateUserCredentials(int index) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "password123");
        return map;
    }
}

