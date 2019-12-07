package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Cell> allFilesPaths;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M
        && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

        }
        else {
            //show the images
            showImage();
        }
    }

    private void showImage(){
        allFilesPaths = new ArrayList<>();
        allFilesPaths = listAllFiles(Environment.getExternalStorageDirectory());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Cell> cells = prepareData();
        ImgAdapter imgAdapter = new ImgAdapter(getApplicationContext(), cells);
        recyclerView.setAdapter(imgAdapter);
        String a = String.valueOf(imgAdapter.getItemCount());
        Toast.makeText(this, a , Toast.LENGTH_SHORT).show();
    }

    //prepare the images for the List
    private ArrayList<Cell> prepareData(){
        ArrayList<Cell> allImages = new ArrayList<>();
        for (Cell c: allFilesPaths){
            Cell cell = new Cell();
            cell.setTitle(c.getTitle());
            cell.setPath(c.getPath());
            allImages.add(cell);
        }
        return  allImages;
    }

    //load all files from on the screen
    private ArrayList<Cell> listAllFiles(File fileName){
        ArrayList<Cell> allFiles = new ArrayList<>();
        File[] files = fileName.listFiles();
            for (int i = 0; i <files.length; i++) {
                Cell cell = new Cell();
                if(files[i].isDirectory()){
                    allFiles.addAll(listAllFiles(files[i]));
                }
                else {
                    if(files[i].getName().endsWith(".jpg")){
                        cell.setTitle(files[i].getName());
                        cell.setPath(files[i].getAbsolutePath());
                        allFiles.add(cell);
                    }
                }
            }
        return allFiles;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //show the images
                showImage();
            }
            else {
                Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
