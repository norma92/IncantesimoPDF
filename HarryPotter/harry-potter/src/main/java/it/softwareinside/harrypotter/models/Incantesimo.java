package it.softwareinside.harrypotter.models;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

@Entity
public class Incantesimo {
    
    @Id
    public String id;
    
    public String name;
    public String incantation;
    public String effect;
    public boolean canBeVerbal;
    public String type;
    public String light;
    public String creator;


    public Incantesimo(String name, String incantion, String effect,
                        boolean canBeVerbal, String type, String light, String creator){

        setName(name);
        setIncantation(incantion);
        setEffect(effect);
        setCanBeVerbal(canBeVerbal);
        setType(type);
        setLight(light);
        setCreator(creator);
    }

    
}


