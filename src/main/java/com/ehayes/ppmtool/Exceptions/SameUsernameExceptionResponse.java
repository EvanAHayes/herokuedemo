package com.ehayes.ppmtool.Exceptions;

public class SameUsernameExceptionResponse {

    private String username;

    public SameUsernameExceptionResponse(String Username) {
        username = Username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String Username) {
        username = Username;
    }
}
