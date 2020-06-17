package com.comtrade.threads;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockThread extends Thread{
    private final JLabel label;

    public ClockThread(JLabel label) {
        this.label = label;
        start();
    }

    @Override
    public void run() {
        while(true){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            label.setText(simpleDateFormat.format(new Date()));

        }
    }
}
