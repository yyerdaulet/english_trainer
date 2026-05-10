package com.example.englishtrainer.bot;

import com.example.englishtrainer.service.CommandHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final CommandHandler commandHandler;

    @Value("${telegram.bot.username}")
    private String botUserName;

    @Value("${telegram.bot.token}")
    private String token;

    public TelegramBot(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }

    @Override
    public String getBotUsername(){
        return botUserName;
    }

    @Override
    public String getBotToken(){
        return token;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            String response = commandHandler.handle(message);
            sendMessage(chatId,response);
        }
    }

    private void sendMessage(Long chatId,String response){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(response);

        try{
            execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
