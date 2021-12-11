package de.hsesslingen.sa.airport.usecases;

import de.hsesslingen.sa.airport.entities.Employee;
import de.hsesslingen.sa.airport.entities.Job;
import de.hsesslingen.sa.airport.interfaceadapters.database.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class EmployeeUseCase {

    private EmployeeRepository employeeRepository;

    public EmployeeUseCase(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        var employees = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }

    public List<Employee> getAllEmployeesByJob(Job job) {
        var employees = new ArrayList<Employee>();
        employeeRepository.findByJob(job).forEach(employees::add);
        return employees;
    }

    public Optional<Employee> getEmployeesById(Long id) {
        return employeeRepository.findById(id);
    }

    public void createEmployee(Employee employee, Consumer<Employee> onSuccess, Consumer<String> onError) {
        validateName(employee.getFirstName(),
                () -> validateName(employee.getLastName(), () -> validateJob(employee.getJob(), () -> {
                    Employee employeeEntity = employeeRepository.save(employee);
                    onSuccess.accept(employeeEntity);
                }, () -> onError.accept("Job muss bekannt sein.")),
                        () -> onError.accept("Nachname darf nicht leer sein.")),
                () -> onError.accept("Vorname darf nicht leer sein."));
    }

    void validateName(String name, Runnable onValid, Runnable onInvalid) {
        if (name.isBlank()) {
            onInvalid.run();
        } else {
            onValid.run();
        }
    }

    void validateJob(Job job, Runnable onKnown, Runnable onUnknown) {
        if (job == Job.AIR_TRAFFIC_CONTROLLER || job == Job.AIRPORT_CLERK) {
            onKnown.run();
        } else {
            onUnknown.run();
        }
    }


    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
