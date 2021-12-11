package de.hsesslingen.sa.airport.interfaceadapters.database;

import de.hsesslingen.sa.airport.entities.Airline;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AirlineRepository extends CrudRepository<Airline, Long> {
    /*Optional<Airline> findById(Long id);*/
}
