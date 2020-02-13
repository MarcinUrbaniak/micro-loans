package com.example.microloans.risk;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class RiskEngineTest {

    @Test
    public void isIncorrectApplicationTime_withTime_5_0_shouldReturnTrue(){
        LocalTime time = LocalTime.of(5,0);
        assertTrue(RiskEngine.isIncorrectApplicationTime(time));
    }

    @Test
    public void isIncorrectApplicationTime_withTime_0_0_shouldReturnFalse(){
        LocalTime time = LocalTime.of(0,0);
        assertFalse(RiskEngine.isIncorrectApplicationTime(time));
    }

    @Test
    public void isIncorrectApplicationTime_withTime_6_0_shouldReturnFalse(){
        LocalTime time = LocalTime.of(6,0);
        assertFalse(RiskEngine.isIncorrectApplicationTime(time));
    }

    @Test
    public void isIncorrectApplicationTime_withTime_7_0_shouldReturnFalse(){
        LocalTime time = LocalTime.of(7,0);
        assertFalse(RiskEngine.isIncorrectApplicationTime(time));
    }
    @Test
    public void isIncorrectAmount_withAmount_4000_shouldReturnFalse(){
        assertFalse(RiskEngine.isIncorrectAmount(BigDecimal.valueOf(4000)));
    }
    @Test
    public void isIncorrectAmount_withAmount_5000_shouldReturnTrue(){
        assertTrue(RiskEngine.isIncorrectAmount(BigDecimal.valueOf(5000)));
    }
    @Test
    public void isIncorrectAmount_withAmount_6000_shouldReturnTrue(){
        assertTrue(RiskEngine.isIncorrectAmount(BigDecimal.valueOf(6000)));
    }

    @Test
    public void isThirdApplicationFromIp_withCounter_1_shouldReturnFalse(){
        assertFalse(RiskEngine.isThirdApplicationFromIp(1));
    }

    @Test
    public void isThirdApplicationFromIp_withCounter_3_shouldReturnTrue(){
        assertTrue(RiskEngine.isThirdApplicationFromIp(3));
    }

    @Test
    public void isThirdApplicationFromIp_withCounter_5_shouldReturnTrue(){
        assertTrue(RiskEngine.isThirdApplicationFromIp(5));
    }

    @Test
    public void isCorrectExtension_withExtension_0_shouldReturnFalse(){
        assertFalse(RiskEngine.isCorrectExtension(0));
    }

    @Test
    public void isCorrectExtension_withExtension_16_shouldReturnFalse(){
        assertFalse(RiskEngine.isCorrectExtension(16));
    }

    @Test
    public void isCorrectExtension_withExtension_14_shouldReturnTrue(){
        assertTrue(RiskEngine.isCorrectExtension(14));
    }

    @Test
    public void isCorrectExtension_withExtension_12_shouldReturnTrue(){
        assertTrue(RiskEngine.isCorrectExtension(12));
    }

}