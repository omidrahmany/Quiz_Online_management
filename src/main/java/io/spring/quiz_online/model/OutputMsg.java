package io.spring.quiz_online.model;

import lombok.Value;

@Value
public class OutputMsg {
    private final String message;


    //todo   true -> success , false -> error  these are class name in js side
    private final boolean typeMessage;

    public OutputMsg(String message, boolean typeMessage) {
        this.message = message;
        this.typeMessage = typeMessage;
    }
}
