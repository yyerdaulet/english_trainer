package com.example.englishtrainer.service;

import org.springframework.stereotype.Service;

@Service
public class CommandHandler {
    public String handle(String message){
        return switch (message.toLowerCase()){
            case "/start" -> "Welcome, I'm English_trainer_bot";
            case "/info" -> "please select your level and go ahead to study english together";
            default -> "Unknown command";
        };
    }
}
