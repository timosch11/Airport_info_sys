package de.hsesslingen.sa.airport.interfaceadapters.database;

import de.hsesslingen.sa.airport.entities.FlightStatus;
import de.hsesslingen.sa.airport.entities.Job;
import de.hsesslingen.sa.airport.entities.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
       Optional<Schedule> findById(Long id);
}

