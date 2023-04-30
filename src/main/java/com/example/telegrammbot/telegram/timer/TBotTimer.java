package com.example.telegrammbot.telegram.timer;

import com.pengrad.telegrambot.TelegramBot;
import com.example.telegrammbot.service.CatService;
import com.example.telegrammbot.service.DogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

;

/**
 *
 */
@Component
@RequiredArgsConstructor
public class TBotTimer {
    private final TelegramBot telegramBot;
//    private final MessageService messageService;
    private final CatService catService;
    private final DogService dogService;


}