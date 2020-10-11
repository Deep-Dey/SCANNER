package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finalproject.method.PDFAdapter;

import java.io.File;
import java.util.ArrayList;

public class Fragment_PDF extends Fragment {
    private Context context;
    ListView lv_pdf;
    public static ArrayList<File> fileList;
    PDFAdapter obj_adapter;
    File dir;

    public Fragment_PDF() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdf, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        lv_pdf=getView().findViewById(R.id.list_view);
        dir=new File(Environment.getExternalStorageDirectory().toString());

        getfile(dir);
        obj_adapter=new PDFAdapter(context,fileList);
        lv_pdf.setAdapter(obj_adapter);

        lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(context,ViewPDF.class);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public void getfile(File dir){
        File listFile[]=dir.listFiles();
        fileList=new ArrayList<>();
        if(listFile!=null && listFile.length>0){
            for(int i=0;i<listFile.length;i++){
                if(listFile[i].getName().endsWith(".pdf") && listFile[i].getName().startsWith("SCANNER_")){
                    fileList.add(listFile[i]);
                }
            }
        }
    }
}