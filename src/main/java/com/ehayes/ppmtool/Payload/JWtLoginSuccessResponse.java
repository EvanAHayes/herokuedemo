package com.ehayes.ppmtool.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWtLoginSuccessResponse {

    //Once login in successful generate token. This is a response to that redux can keep the token
    //and send it back with any request if token still valid

    private boolean success;
    private String token;

    @Override
    public String toString() {
        return "JWtLoginSuccessResponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}
