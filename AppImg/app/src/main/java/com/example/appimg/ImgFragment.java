package com.example.appimg;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImgFragment extends Fragment {

    List<Cell> allFilesPaths;
    private StaggeredGridLayoutManager sglmanager = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img, container, false);


        //cho phép truy cập vào kho lưu trữ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        && ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
        else {
            //hiển thị ảnh
            showImage(view);
        }
        return view;
    }

    //Hiển thị ảnh lên màn hình
    private void showImage( View view){
        //đây là folder chứa tất cả ảnh
        allFilesPaths = new ArrayList<>();
        allFilesPaths = listAllFiles(Environment.getExternalStorageDirectory());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_img_gv_item);
        recyclerView.setHasFixedSize(true);

        //tạo list cùng với 3 cột
        sglmanager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglmanager);

        ArrayList<Cell> cells = prepareData();
        ImgAdapter adapter = new ImgAdapter(getContext(), cells);
        recyclerView.setAdapter(adapter);

        String a = String.valueOf(adapter.getItemCount());
        Toast.makeText(getContext(), a + " ảnh" , Toast.LENGTH_SHORT).show();
    }

    //prepare ảnh trong list
    private ArrayList<Cell> prepareData(){
        ArrayList<Cell> allImgs = new ArrayList<>();
        for (Cell c: allFilesPaths){
            Cell cell = new Cell();
            cell.setTitle(c.getTitle());
            cell.setPath(c.getPath());
            allImgs.add(cell);
        }
        return allImgs;
    }


    //lấy tất cả ảnh trong folder
    private ArrayList<Cell> listAllFiles(File fileName){
        ArrayList<Cell> allFiles = new ArrayList<>();
        File[] files = fileName.listFiles();
        for (int i = 0; i <files.length; i++) {
            Cell cell = new Cell();

            //kiểm tra xem tệp được biểu thị bởi tên đường dẫn trừu tượng này có phải là một thư mục không.
            if(files[i].isDirectory()){

                //mở các file nhỏ có trong thư mục files[i] ra để tìm kiếm ảnh
                allFiles.addAll(listAllFiles(files[i]));
            }
            else {
                if(files[i].getName().endsWith(".jpg"))
                {
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
        if (requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //hiển thị ảnh
                LayoutInflater layoutInflater = ImgFragment.this.getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.fragment_img, null);
                showImage(view);
            }
            else {
                Toast.makeText(getContext(), "Permission not granted!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}
