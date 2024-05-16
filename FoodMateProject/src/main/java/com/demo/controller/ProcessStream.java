package com.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessStream implements Runnable {
    
    private static final String UTF_8 = "utf-8";
    private InputStream inputStream;
    private StringBuilder sb;

    public ProcessStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.sb = new StringBuilder();
    }

    public String start() throws InterruptedException {
        Thread thread = new Thread(this);
        thread.start();
        thread.join();

        return sb.toString();
    }

    @Override
    public void run() {
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            isr = new InputStreamReader(inputStream, UTF_8);
            br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getResult() {
        return sb.toString();
    }
}
