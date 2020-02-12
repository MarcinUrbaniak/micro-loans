package com.example.microloans.risk;

import com.example.microloans.model.type.LoanValueParameter;

import java.math.BigDecimal;
import java.time.LocalTime;

public class RiskEngine {

    private static final LocalTime timeMin = LocalTime.of(0, 0);
    private static final LocalTime timeMax = LocalTime.of(6, 0);
    private static final int noOfAcceptedRequestFromIp = 2;
    private static final int maxExtension = 14;

    public static boolean isIncorrectApplicationTime(LocalTime time) {
        boolean timeIsAfter = time.isAfter(timeMin);
        boolean timeIsBefore = time.isBefore(timeMax);
        return timeIsAfter && timeIsBefore;
    }

    public static boolean isIncorrectAmount(BigDecimal amount) {
        return amount.compareTo(LoanValueParameter.MAX_VALUE.getValue()) >= 0;
    }

    public static boolean isThirdApplicationFromIp(int numberOfIpAddresses) {
        return noOfAcceptedRequestFromIp < numberOfIpAddresses;
    }

    public static boolean isCorrectExtension(int extension) {
        return (extension > 0 && extension <= maxExtension);
    }

}