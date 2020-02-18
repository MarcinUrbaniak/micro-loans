package com.example.microloans.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.*;

@Configuration
public class ClockConfiguration {

    //private final static LocalDate LOCAL_DATE = LocalDate.of(2019, 12, 17);
    private final static Instant FIXED_TIME = Instant.parse("2020-02-12T04:00:00.00Z");

    @Bean
    @ConditionalOnMissingBean
    Clock getSystemDefaultZoneClock() {
        return Clock.systemDefaultZone();
    }

//    Clock clock = Clock.fixed(Instant.parse("2020-02-12T05:00:00.00Z"), ZoneId.of("UTC"));
//    LocalTime newNow = LocalTime.now(clock);

    @Bean
    @Profile("test")
    @Primary
    Clock getFixedClock() {
        return Clock.fixed(FIXED_TIME, ZoneId.systemDefault());
    }
}
