package com.netreaders.utils;

import com.netreaders.models.ResponseMessage;

public class ResponseMessagePrepearer {
    public static <T> void prepareMessage(ResponseMessage<T> rm, String message) {
        rm.setIsSuccessful(false);
        rm.setErrorMessage(message);
    }
}
