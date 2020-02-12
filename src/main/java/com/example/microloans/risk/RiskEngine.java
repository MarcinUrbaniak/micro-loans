package com.example.microloans.risk;

import com.example.microloans.model.type.LoanValueParameter;

import java.math.BigDecimal;
import java.time.LocalTime;

public class RiskEngine {

    private static final LocalTime timeMin = LocalTime.of(19,0);
    private static final LocalTime timeMax = LocalTime.of(23,0);
    private static final int noOfAcceptedRequestFromIp = 2;

    public static boolean isIncorrectApplicationTime(LocalTime time){
        boolean timeIsAfter = time.isAfter(timeMin);
        boolean timeIsBefore = time.isBefore(timeMax);
        if(timeIsAfter && timeIsBefore ) {
            return true;
        }
        return false;
    }

    public static boolean isIncorrectAmount(BigDecimal amount){
        return amount.compareTo(LoanValueParameter.MAX_VALUE.getValue()) >= 0;
    }

    public static boolean isThirdApplicationFromIp(int numberOfIpAddresses) {
        boolean temp = noOfAcceptedRequestFromIp < numberOfIpAddresses;
        System.out.println("temp = " + temp);
        return noOfAcceptedRequestFromIp < numberOfIpAddresses;
    }

}