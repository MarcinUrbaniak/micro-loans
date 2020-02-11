package com.example.microloans.model.type;

import java.math.BigDecimal;

public enum LoanValueParameter {
    MAX_VALUE( BigDecimal.valueOf(5000)), MIN_VALUE(BigDecimal.ZERO);

    private final BigDecimal value;

    LoanValueParameter(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
