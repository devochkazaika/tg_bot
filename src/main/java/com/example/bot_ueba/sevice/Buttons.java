package com.example.bot_ueba.sevice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import jakarta.inject.Singleton;



public class Buttons {

    public static HashSet<Integer> queset = new HashSet<Integer>();

    public static HashSet<String> userset = new HashSet<String>();

    List<List<InlineKeyboardButton>> mas = new ArrayList<>();

    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();


    Buttons(){
        inlineKeyboardMarkup = new InlineKeyboardMarkup();
        for (int j=0; j<3; j++){
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            for (int i=0; i<4; i++){
                if (!queset.contains(i + j*4)){
                    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                    inlineKeyboardButton1.setText(Integer.toString(i + j * 4));
                    inlineKeyboardButton1.setCallbackData(Integer.toString(i + j * 4));
                    keyboardButtonsRow.add(inlineKeyboardButton1);
                }
                else continue;

            }
            mas.add(keyboardButtonsRow);
        }
        inlineKeyboardMarkup.setKeyboard(mas);
    }

    public SendMessage sendInlineKeyBoardMessage(Long chatId) {
        var mas = this.get_buttons(); //List<List<InlineKeyboardButton>>
        inlineKeyboardMarkup.setKeyboard(mas);
        System.out.println(mas.size());
        String textToSend = "Голосование";
        SendMessage q = new SendMessage();
        q.setChatId(String.valueOf(chatId));
        q.setText(textToSend);
        q.setReplyMarkup(inlineKeyboardMarkup); 
        return q;
    }

    public InlineKeyboardMarkup getMarkup(){
        return inlineKeyboardMarkup;
    }

    public List<List<InlineKeyboardButton>> get_buttons(){
        return mas;
    }

    //Singelton в действии мб сработает только голосовуха одна будет)
    // public static Buttons instance = null;
    // public static synchronized Buttons getInstance() {
    //     if (instance == null)
    //         instance = new Buttons();
    //     return instance;
    // }

}
