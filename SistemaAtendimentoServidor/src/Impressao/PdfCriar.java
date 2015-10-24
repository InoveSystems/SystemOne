/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Impressao;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PdfCriar {

    public void criarpdf(String ficha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println(sdf.format(new Date()));
        Document document = new Document(PageSize.B4, 10, 10, 10, 10);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(getClass().getResource("/Config/SENHAS.pdf").getFile()));
            document.open();
            // adicionando um parágrafo ao documento 
            Font f = new Font(FontFamily.HELVETICA, 15, Font.NORMAL);
            document.add(new Paragraph("    ..:: SANAR FARMÁCIAS ::..", f));
            Font g = new Font(FontFamily.HELVETICA, 15, Font.NORMAL);
            document.add(new Paragraph("                   SENHA ", g));
            Font H = new Font(FontFamily.HELVETICA, 40, Font.BOLD);
            document.add(new Paragraph("     " + ficha, H));
            document.add(new Paragraph(" ", f));
            document.add(new Paragraph("           " + sdf.format(new Date()), f));
            document.add(new Paragraph("--------------------------------------------", f));
            document.add(new Paragraph("..:: 3D Soluções Tecnológicas ::..    ", f));

        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        document.close();

    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
