package com.example.appimg;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;

public class ImgFragment extends Fragment {

    GridView gridView;
    ArrayList<File> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img, container, false);

        gridView = (GridView) view.findViewById(R.id.fragment_img_gv_item);

        list = imageReader(Environment.getExternalStorageDirectory());

        gridView.setAdapter(new gridAdapter());
        return view;
    }

    public class gridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view = null;

            if (view == null){
                view = getLayoutInflater().inflate(R.layout.img_item, parent,false);
                ImageView imageView = (ImageView) view.findViewById(R.id.img_item_iv_item);
                imageView.setImageURI(Uri.parse(list.get(i).toString()));
            }

            return  view;
        }
    }

    private ArrayList<File> imageReader(File externalStorageDirectory) {

        ArrayList<File> b = new ArrayList<>();
        File[] files = externalStorageDirectory.listFiles();

        for(int i = 0; i < files.length; i++){

            if (files[i].isDirectory()){
                b.addAll(imageReader(files[i]));
            }
            else {
                if(files[i].getName().endsWith(".jpg")){
                    b.add(files[i]);
                }
            }
        }
        return b;
    }
}
