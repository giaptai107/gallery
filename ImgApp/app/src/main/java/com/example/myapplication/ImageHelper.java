package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//cách giảm bớt size của ảnh để có hiệu suất tốt hơn
public class ImageHelper {

    public static int calSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSpamleSize = 1;

        if(height > reqHeight && width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSpamleSize) >= reqHeight &&
                    (halfWidth / inSpamleSize) >= reqWidth){
                inSpamleSize *= 2;
            }
        }
        return inSpamleSize;
    }

    public static Bitmap decodeSampleBitmapFromPath(String pathName, int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        options.inSampleSize = calSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeFile(pathName, options);
    }
}