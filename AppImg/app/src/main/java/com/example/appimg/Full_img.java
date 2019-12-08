package com.example.appimg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.net.URI;

public class Full_img extends AppCompatActivity {

    ImageView fullimage;
    ViewFlipper vflipper;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_img);

        //fullimage = (ImageView) findViewById(R.id.activity_full_img_iv_view);
        final String data = getIntent().getExtras().getString("img");
        String data2 = getIntent().getExtras().getString("name");
//        setTitle(data2.toString());
        //fullimage.setImageURI(Uri.parse(data));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageURI(Uri.parse(data));


        //Xoa anh
        btn = (Button) findViewById(R.id.btnDel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                this.DeleteImage();
                PhotoView pv = (PhotoView) findViewById(R.id.photo_view);
                pv.setImageDrawable(null);
                

            }

            private void DeleteImage() {
                String path = Uri.parse(data).getPath();
                Log.e("-->", " Da xoa file:" + path);
                File fdelete = new File(path);
                if (fdelete.exists()) {
                    fdelete.delete();
                }
            }
        });
    }
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
