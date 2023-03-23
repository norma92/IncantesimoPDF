package it.softwareinside.harrypotter.services;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.softwareinside.harrypotter.models.Incantesimo;
import it.softwareinside.harrypotter.repository.IncantesimoRepository;

@Service
public class IncantesimoService {
    
    @Autowired
    IncantesimoRepository incantesimoR;

    /**
     * il metodo restituisce un incantesimo preso dall'array che viene restituito dall'API
     * 
     * @return
     */
    public Incantesimo[] generate(){
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Incantesimo[]> incantesimo = rt.getForEntity("https://wizard-world-api.herokuapp.com/Spells/", Incantesimo[].class);
        return incantesimo.getBody();
    }

    /**
     * il metodo restituisce il numero di incantesimi presenti nell'API
     * 
     * lo useremo nel metodo add per aggiungere un incantesimo dell'API al nostro database,
     * senza dover passare un id al metodo
     * 
     * @return
     */
    public int quantita(){
        try {
            int quantita = generate().length;
            return quantita;
            
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return -1;
        }
    }


    public boolean isIdHere(String id){
        for (Incantesimo elemento : database()) {
            if(elemento.id.equals(id))
                return true;
        }
        return false;
    }

    /**
     * il metodo serve per aggiungere un incantesimo al nostro database,
     * viene effettuata una verifica se l'incantesimo è già presente nel database
     * restituisce un messaggio affermativo nel caso in cui l'operazione sia andata a buon fine
     * altrimenti restituisce un messaggio negativo, mostrando in console il tipo di errore
     * @return
     */
    public String add(){
        try {
            Random casuale = new Random();
            Incantesimo nuovo = (generate()[casuale.nextInt(1,quantita())]);
            if(!isIdHere(nuovo.getId())){
                incantesimoR.save(nuovo);
                return "incantesimo aggiunto";
            }
            return "non è stato possibile aggiungere l'incantesimo" + nuovo.getId();
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return "non è stato possibile aggiungere l'incantesimo";
        }
    }

    /**
     * il metodo serve ad eliminare l'incantesimo del quale passiamo l'id dal database,
     * restituisce un messaggio affermativo nel caso in cui l'operazione sia andata a buon fine
     * altrimenti restituisce un messaggio negativo, mostrando in console il tipo di errore
     * 
     * @param id
     * @return
     */
    public String delete(String id){
        try {
            incantesimoR.deleteById(id);
            return "incantesimo rimosso";
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return "non è stato possibile eliminare l'incantesimo";
        }
    }
    
    /**
     * il metodo serve ad eliminate tutti gli elementi (tuple) presenti nel database,
     * deve verificarsi una corrispondenza tra il codice inserito e quello effettivo per far sì che l'operazione venga eseguita,
     * verrà restituito un messaggio affermativo nel caso in cui l'operazione sia andata a buon fine
     * altrimenti restituisce un messaggio negativo, mostrando in console il tipo di errore
     * @param pw
     * @return
     */
    public String deleteAll(int pw){
        if(pw == 1234){
            try {
                incantesimoR.deleteAll();
                return "sono stati rimossi tutti gli elementi del database";
            } catch (Exception e) {
                System.out.println("errore: " + e);
                return "non è stato possibile eliminare tutti gli elementi dal database";
            }
        }
        return "non è stato possibile eliminare tutti gli elementi dal database";
     
    }

    /**
     * il metodo restituisce tutti gli elementi presenti nel database
     * nel caso in cui non sia possibile eseguire l'operazione viene stampato in console il tipo di errore
     * e il metodo restituisce un null
     * 
     * @return
     */
    public Iterable<Incantesimo> database(){
        try {
            return incantesimoR.findAll();
            
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return null;
        }
    }

    /**
     * il metodo serve per recuperare l'elemento del quale indichiamo l'id, presente nel database
     * se l'operazione non è andata a buon fine viene stampato in console il tipo di errore
     * e il metodo restituisce un null
     * 
     * @param id
     * @return
     */
    public Incantesimo find(String id){
        try {
            return incantesimoR.findById(id).get();
            
        } catch (Exception e) {
            System.out.println("errore: " + e);
            return null;
        }
    }

}
