package com.example.anotaesoficial.config;

import java.text.SimpleDateFormat;

public class DataForm {

    public static String dataAtual(){
        Long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }

}
