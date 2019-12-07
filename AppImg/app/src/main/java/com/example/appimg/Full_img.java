package com.example.appimg;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

public class Full_img extends AppCompatActivity {

    ImageView fullimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_img);

        //fullimage = (ImageView) findViewById(R.id.activity_full_img_iv_view);
        String data = getIntent().getExtras().getString("img");
        String data2 = getIntent().getExtras().getString("name");
//        setTitle(data2.toString());
        //fullimage.setImageURI(Uri.parse(data));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageURI(Uri.parse(data));
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//
//            default:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}