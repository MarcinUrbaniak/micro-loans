package com.example.microloans.api.response;

public class CreateUserAccountResponse extends BasicResponse {

    private Long userId;

    public CreateUserAccountResponse() {
    }

    public CreateUserAccountResponse(String responseMsg, Long userId) {
        super(responseMsg);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
