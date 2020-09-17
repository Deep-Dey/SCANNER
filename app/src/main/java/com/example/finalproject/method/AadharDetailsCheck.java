package com.example.finalproject.method;

public class AadharDetailsCheck {
    public static String checkAadhar(String text){
        text=text.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<PrintLetterBarcodeData ","YOUR AADHAAR DETAILS\n");
        text=text.replace("uid","ID");
        text=text.replace("name", "\nNAME");
        text=text.replace("gender", "\nGENDER");
        text=text.replace("yob", "\nYEAR OF BIRTH");
        text=text.replace("gname", "\nGUARDIAN NAME");
        text=text.replace("house", "\nHOUSE");
        text=text.replace("street", "\nSTREET");
        text=text.replace("lm", "\nLM");
        text=text.replace("loc", "\nLOC");
        text=text.replace("vtc", "\nVTC");
        text=text.replace("po", "\nPOST OFFICE");
        text=text.replace("subdist=", "\nSUB-DIST=");
        text=text.replace("dist", "\nDIST");
        text=text.replace("state", "\nSTATE");
        text=text.replace("pc", "\nPIN CODE");
        text=text.replace("dob", "\nDATE OF BIRTH");
        text=text.replace("/>", "");

        return text;
    }
}
