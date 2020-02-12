package com.example.microloans.api.request;

public class ExtensionOfLoanRequest {

    private Long loanId;
    private int extension;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public int getExtension() {
        return extension;
    }

    public void setExtension(int extension) {
        this.extension = extension;
    }
}
