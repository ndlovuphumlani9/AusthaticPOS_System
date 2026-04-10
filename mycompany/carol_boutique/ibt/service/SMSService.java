/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carol_boutique.ibt.service;
import java.io.IOException;

import java.util.Base64;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SMSService {
    
    private static final String USERNAME = "slickrick"; 
    private static final String PASSWORD = "MATHOthoana@20"; 
    private static final String SENDER = "InfoSMS"; 
    private static final String BASE_URL = "https://dkp311.api.infobip.com"; 

    public static boolean sendSMS(String phoneNumber, String messageText) {
        String auth = USERNAME + ":" + PASSWORD;
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());

        String jsonBody = String.format(
            "{\"messages\":[{\"from\":\"%s\",\"destinations\":[{\"to\":\"%s\"}],\"text\":\"%s\"}]}",
            SENDER, phoneNumber, messageText);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody);

        
        Request request = new Request.Builder()
            .url(BASE_URL + "/sms/2/text/advanced")
            .addHeader("Authorization", "App 4ff90bcd07b5dd97d1623b17f68b00ab-c55a4ba9-d3e3-4f41-85fa-4ded732f7a86")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .post(requestBody)
            .build();

        
        OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
        try {
            Response response = httpClient.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
