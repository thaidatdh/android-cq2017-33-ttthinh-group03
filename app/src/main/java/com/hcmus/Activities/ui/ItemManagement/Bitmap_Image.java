package com.hcmus.Activities.ui.ItemManagement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Bitmap_Image {
    private URL url;
    public Bitmap bmp;
    public Bitmap_Image(String url){
        URL url3 = null;
        try {
            url3 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url3.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
