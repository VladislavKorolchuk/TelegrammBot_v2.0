package com.example.telegrammbot.telegram.timer;

import com.pengrad.telegrambot.TelegramBot;
import js6team3.tbot.service.CatService;
import js6team3.tbot.service.DogService;
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