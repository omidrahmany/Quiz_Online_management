package io.spring.quiz_online.model;

import lombok.Value;

@Value
public class OutputMsg {
    private final String message;

    public OutputMsg(String message) {
        this.message = message;
    }
}
