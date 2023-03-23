package it.softwareinside.harrypotter.restController;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import it.softwareinside.harrypotter.models.Incantesimo;
import it.softwareinside.harrypotter.services.IncantesimoService;
import it.softwareinside.harrypotter.services.PdfService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class IncantesimoRestController {
    
    @Autowired
    IncantesimoService incantesimoS;

    @Autowired
    PdfService pdfS;

    @GetMapping("/print-spell")
    public Incantesimo print() {
        return incantesimoS.generate()[0];  
    }
    
    @GetMapping("/add-spell")
    public String add(){
        return incantesimoS.add();
    }

    @DeleteMapping("/delete-spell")
    public String delete(@RequestParam String id){
        return incantesimoS.delete(id);
    }

    @DeleteMapping("/delete-all")
    public String deleteAll(@RequestParam int pw){
        return incantesimoS.deleteAll(pw);
    }

    @GetMapping("/database")
    public Iterable<Incantesimo> database(){
        return incantesimoS.database();
    }

    @GetMapping("/find-spell")
    public Incantesimo find(@RequestParam String id){
        return incantesimoS.find(id);
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generaNota(@RequestParam("id") String id) {
    	
    	try {

            ByteArrayInputStream bis =  pdfS.generaPDF(id) ; 

            var headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.pdf");
    
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
    		
    	}catch (Exception e) {
			return null;
    	
        }
    }

    @RequestMapping(value = "/pdf-database", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> stampaPDF() {
        
        try {

            ByteArrayInputStream bis =  pdfS.databasePDF() ; 

            var headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=example.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
            
        }catch (Exception e) {
            return null;
        }
    }
}

