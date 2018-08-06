package com.gtzhou.blog.domain;

import lombok.Data;

@Data
public class ServerMessage {
    private String responseMessage;

    public ServerMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
