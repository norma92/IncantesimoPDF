package it.softwareinside.harrypotter.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;




@Service
public class PdfService {
	
	@Autowired
	IncantesimoService incantesimoS;
	
	
	
	public  ByteArrayInputStream generaPDF(String id) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            
            Paragraph contenuto = new Paragraph();

            contenuto.add(incantesimoS.find(id).toString());

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(contenuto);
            document.close();

        } catch (DocumentException ex) {

           return null;
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
	

    public  ByteArrayInputStream databasePDF() {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            
            Paragraph contenuto = new Paragraph();

            contenuto.add(incantesimoS.database().toString());

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(contenuto);
            document.close();

        } catch (DocumentException ex) {

           return null;
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
