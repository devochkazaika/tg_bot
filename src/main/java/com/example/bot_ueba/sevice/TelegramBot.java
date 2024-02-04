package com.example.bot_ueba.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.bot_ueba.config.BotConfig;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;


    public TelegramBot(BotConfig config) {this.config = config;
    }

    String messageText;

    Buttons menu = new Buttons();

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    
    @Override
    public void onUpdateReceived(Update update){
        
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    //startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    try{
                        execute(menu.sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                    }
                    catch (TelegramApiException e){
                        e.printStackTrace();
                    }
                case "/clear":
                    Buttons.queset.clear();

                    break;

                default: sendMessage(chatId, "no comand");
            }
        }
        else if (update.hasCallbackQuery()) {
            int call_data = Integer.parseInt(update.getCallbackQuery().getData());
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            var name = update.getCallbackQuery().getMessage().getChat().getUserName();
            if (!Buttons.queset.contains(call_data) && !Buttons.userset.contains(name)){
                sendMessage(chat_id, "@" + name + " Занял " + call_data + " место");
                Buttons.queset.add(call_data); 
                // DeleteMessage deleteMessage = new DeleteMessage();
                // deleteMessage.setMessageId(update.getMessage().getMessageId());               
                EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
                editMessageReplyMarkup.setChatId(String.valueOf(chat_id));
                editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                editMessageReplyMarkup.setReplyMarkup(new Buttons().getMarkup());
                try {
                    execute(editMessageReplyMarkup);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                Buttons.userset.add(name);

            }
            // DeleteMessage deleteMessage = new DeleteMessage();
            //     deleteMessage.setChatId(update.getMessage().getChatId().toString());
            //     deleteMessage.setMessageId(update.getMessage().getMessageId());                
            //     //пауза
            //     try{
            //         execute(deleteMessage);
            //     }
            //     catch (TelegramApiException e){
            //             e.printStackTrace();
            //         }
        }
    }

    private void startCommandReceived(long chatId, String name){

        String answer = "Hi, " + name + ", nice to meet you";
        sendMessage(chatId, answer);

    }


    // public SendMessage sendInlineKeyBoardMessage(Long chatId) {
    //     InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    //     var mas = menu.get_buttons(); //List<List<InlineKeyboardButton>>
    //     inlineKeyboardMarkup.setKeyboard(mas);
    //     System.out.println(mas.size());
    //     String textToSend = "Голосование";
    //     SendMessage q = new SendMessage();
    //     q.setChatId(String.valueOf(chatId));
    //     q.setText(textToSend);
    //     q.setReplyMarkup(inlineKeyboardMarkup); 
    //     return q;
    // }



    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        }
        catch (TelegramApiException e){
            
        }
    }

    public BotConfig getConfig() {
        return config;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
