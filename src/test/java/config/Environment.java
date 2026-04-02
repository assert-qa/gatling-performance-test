package config;

public class Environment {
    public static final String BASE_URL = 
        System.getProperty("baseUrl", "https://restful-booker.herokuapp.com");
    
    public static final String BASE_URL_PATH = "/";
    
    public static final int TIMEOUT = Integer.parseInt(
        System.getProperty("timeout", "5000"));
}

