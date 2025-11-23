package com.example.user_service.service.bot;

public interface TextSender<M> {
    void sendText(M message) throws Exception;
}
