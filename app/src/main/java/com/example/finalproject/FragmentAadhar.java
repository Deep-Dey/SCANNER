package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.method.saveAsPdf;

public class FragmentAadhar extends Fragment {

    public static TextView aadhaar_tv;
    Button scan_bt, save_bt;
    private Context context;
    public FragmentAadhar() {
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
        return inflater.inflate(R.layout.fragment_aadhar, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        aadhaar_tv=getView().findViewById(R.id.result_text);
        scan_bt=getView().findViewById(R.id.btn_scan);
        scan_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,CoreActivityofAadhar.class));
            }
        });

        save_bt=getView().findViewById(R.id.btn_save);
        save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAsPdf.convertPDF(context, aadhaar_tv.getText().toString());
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }
}