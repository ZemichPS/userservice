package com.example.user_service.service.bot;

import com.example.user_service.service.UserService;
import com.example.user_service.service.statemachine.StateMachineService;
import com.example.user_service.service.statemachine.registration.RegistrationEvent;
import io.github.natanimn.telebof.BotClient;
import io.github.natanimn.telebof.BotContext;
import io.github.natanimn.telebof.annotations.MessageHandler;
import io.github.natanimn.telebof.enums.MessageType;
import io.github.natanimn.telebof.requests.send.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class BotMessageHandler {

    private final BotClient botClient;
    private final StateMachineService<RegistrationEvent> stateMachineService;
    private final UserService userService;


    @MessageHandler(commands = "регистрация")
    void start(BotContext context, Message message){

        context.sendMessage(message.chat.id, "Welcome to my echo bot! 👋").exec();
    }
}
