package io.spring.quiz_online.model;

import lombok.Value;

@Value
public class OutputMsg {
    private final String message;

    //todo messageType definition:  true -> success , false -> error  these are class name in js side
    private final boolean messageType;

    public OutputMsg(String message, boolean messageType) {
        this.message = message;
        this.messageType = messageType;
    }
}
