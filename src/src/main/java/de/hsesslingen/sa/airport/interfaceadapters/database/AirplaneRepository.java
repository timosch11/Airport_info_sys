package de.hsesslingen.sa.airport.interfaceadapters.database;

import de.hsesslingen.sa.airport.entities.Airplane;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AirplaneRepository extends CrudRepository<Airplane, Long> {
    Optional<Airplane> findById(Long id);
}
