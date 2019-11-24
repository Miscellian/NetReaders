package com.netreaders.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseMessage<T> {
    private Boolean isSuccessful = true;
    private String errorMessage = "";
    private T obj = null;
}
