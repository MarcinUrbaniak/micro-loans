package com.example.microloans.model.type;

public enum LoanPeriodParameter {
    MIN_PERIOD(0L), MAX_PERIOD(365L);

    private final Long period;

    LoanPeriodParameter(Long period) {
        this.period = period;
    }

    public Long getPeriod() {
        return period;
    }
}
