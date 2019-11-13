package com.netreaders.services;

import com.netreaders.models.ResponseMessage;

public class ResponseMessagePrepearer {
	public static <T> void prepareMessage(ResponseMessage<T> rm, String message) {
		rm.setSuccessful(false);
		rm.setErrorMessage(message);
	}
}
