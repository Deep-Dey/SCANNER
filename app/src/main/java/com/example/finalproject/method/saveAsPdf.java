package com.example.finalproject.method;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class saveAsPdf {
    public static void convertPDF(Context context, String txt){
        Document document=new Document();
        String Filename=new SimpleDateFormat("yyyymmdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        String FilePath= Environment.getExternalStorageDirectory()+"/"+"SCANNER_"+Filename+".pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FilePath));
            document.open();
            document.add(new Paragraph(txt));
            document.close();
            Toast.makeText(context,Filename+".pdf\nis saved to\n"+FilePath,Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException | DocumentException e) {
            Toast.makeText(context,"Problem occur",Toast.LENGTH_SHORT).show();
        }
    }
}
