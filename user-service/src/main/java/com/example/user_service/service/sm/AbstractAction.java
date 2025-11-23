package com.example.user_service.service.sm;

import com.example.user_service.service.bot.TelegramTextSender;
import org.springframework.statemachine.action.Action;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class AbstractAction<S,E> implements Action<S,E> {

    protected final TelegramTextSender telegramTextSender;

    protected AbstractAction(TelegramTextSender telegramTextSender) {
        this.telegramTextSender = telegramTextSender;
    }

    abstract protected SendMessage createSendMessage(String chatId);

}
