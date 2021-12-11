package de.hsesslingen.sa.airport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirportApplication {

    public static final int RUNWAY_COUNT = 0;

    public static void main(String[] args) {
        SpringApplication.run(AirportApplication.class, args);
    }

}
