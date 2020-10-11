package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class ViewPDF extends AppCompatActivity {
    PDFView pdfView;
    int position=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        pdfView=findViewById(R.id.pdfView);
        position=getIntent().getIntExtra("position", -1);
        displayPDF();
    }

    public void displayPDF(){
        pdfView.fromFile(Fragment_PDF.fileList.get(position))
                .enableSwipe(true)
                .enableDoubletap(true)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
}