package com.example.ecounity.activity.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WhatsAppApi {

    public static void sendWhatsAppMessage(Context context, String phoneNumber, String message) throws UnsupportedEncodingException {
        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode(message, "UTF-8");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (isValidPhoneNumber(phoneNumber)) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        // Implement phone number validation logic here
        return true; // Replace with actual validation logic
    }


}