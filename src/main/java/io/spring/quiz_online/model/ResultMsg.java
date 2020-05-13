package io.spring.quiz_online.model;

import lombok.Value;

@Value
public class ResultMsg {
    private final String message;

    //todo messageType definition:  true -> success , false -> error  these are class name in js side
    private final boolean messageType;

    public ResultMsg(String message, boolean messageType) {
        this.message = message;
        this.messageType = messageType;
    }
}
