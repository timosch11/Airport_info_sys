package de.hsesslingen.sa.airport.interfaceadapters.database;

import de.hsesslingen.sa.airport.entities.Employee;
import de.hsesslingen.sa.airport.entities.Job;
import org.springframework.data.repository.CrudRepository;

// spring implements all methods declared in CrudRepository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    Iterable<Employee> findByJob(Job job);
}
