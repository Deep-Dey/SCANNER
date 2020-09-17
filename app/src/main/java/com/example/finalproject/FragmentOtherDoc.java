package com.example.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.method.saveAsPdf;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class FragmentOtherDoc extends Fragment {
    private Context context;
    ImageView imageView;
    TextView tv;
    public static final int IMAGE_PICK_CODE=1000;
    public static final int REQUEST_CODE = 102;

    Button converter, camera, imagePick, pdf;

    public FragmentOtherDoc() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other_doc, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        imageView=getView().findViewById(R.id.image);
        tv=getView().findViewById(R.id.tv_1);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        }

        converter=getView().findViewById(R.id.textConvert);
        converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTextFromImage();
            }
        });

        camera=getView().findViewById(R.id.openCamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCamera();
            }
        });

        imagePick=getView().findViewById(R.id.imagePick);
        imagePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        pdf=getView().findViewById(R.id.savePDF);
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAsPdf.convertPDF(context, tv.getText().toString());
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public void getTextFromImage(){
        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();

        TextRecognizer textRecognizer=new TextRecognizer.Builder(context).build();

        if(!textRecognizer.isOperational()){
            Toast.makeText(context,"Could not find text",Toast.LENGTH_SHORT).show();
        }
        else{
            Frame frame=new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> item=textRecognizer.detect(frame);
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<item.size();++i){
                TextBlock myitem=item.valueAt(i);
                sb.append(myitem.getValue());
                sb.append("\n");
            }
            tv.setText(sb.toString());
        }
    }

    public void pickImage(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    public void OpenCamera(){
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, REQUEST_CODE);
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_PICK_CODE){
            imageView.setImageURI(data.getData());
        }
        else if(requestCode==REQUEST_CODE){
            Bitmap image=(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);
        }
    }
}