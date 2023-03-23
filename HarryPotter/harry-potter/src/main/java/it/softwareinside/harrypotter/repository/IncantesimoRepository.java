package it.softwareinside.harrypotter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.softwareinside.harrypotter.models.Incantesimo;

@Repository
public interface IncantesimoRepository extends CrudRepository<Incantesimo, String>{
    
}
