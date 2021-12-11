package de.hsesslingen.sa.airport.interfaceadapters.database;

import de.hsesslingen.sa.airport.entities.AirplaneType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AirplaneTypeRepositiory extends CrudRepository<AirplaneType, Long> {
    Optional<AirplaneType> findById(Long id);
}
