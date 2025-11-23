package com.example.user_service.service.bot;

import com.example.user_service.service.sm.UpdateResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private final UpdateResolver updateResolver;

    @Override
    public void consume(Update update) {
        updateResolver.resolve(update);
    }
}
