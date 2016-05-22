package com.wonderfan;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfArray;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class App 
{
    public static final String SRC = "result/javascript.pdf";
    public static final String DEST = "result/changed.pdf";
 
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new App().manipulatePdf(SRC, DEST);
    }
 
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        for(int i=1; i<= reader.getNumberOfPages(); i++){
            PdfDictionary dict = reader.getPageN(i);
            //System.out.println(dict.getKeys());
            PdfArray elements = dict.getAsArray(PdfName.CONTENTS);
            //for(int i=0; i< elements.size();i++){
                PdfObject object = elements.getDirectObject(elements.size()-1);
                //System.out.println(object.isStream());
                if (object instanceof PRStream) {
                    PRStream stream = (PRStream)object;
                    byte[] data = PdfReader.getStreamBytes(stream);
                    stream.setData(new String(data,"UTF8").replace("www.it-ebooks.info", "wechat:086opensource").getBytes());
                }           
            //}
        }
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        Map<String, String> info = reader.getInfo();
        info.remove("www.it-ebooks.info");
        stamper.setMoreInfo(info);
        stamper.close();
        reader.close();
    }    
}
