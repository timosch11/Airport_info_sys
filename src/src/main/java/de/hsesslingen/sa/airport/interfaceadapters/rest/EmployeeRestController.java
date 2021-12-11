package de.hsesslingen.sa.airport.interfaceadapters.rest;

import de.hsesslingen.sa.airport.entities.Employee;
import de.hsesslingen.sa.airport.entities.Job;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDto;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDtoWithArg;
import de.hsesslingen.sa.airport.usecases.EmployeeUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class EmployeeRestController extends BaseController {
    private static final String EMPLOYEES_ENDPOINT = "/api/employees";
    private static final String EMPLOYEES_ID_ENDPOINT = EMPLOYEES_ENDPOINT + "/{id}";

    private EmployeeUseCase employeeUseCase;

    public EmployeeRestController(EmployeeUseCase employeeUseCase) {
        this.employeeUseCase = employeeUseCase;
    }

    @RequestMapping(path = EMPLOYEES_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<List<Employee>> getAll(@RequestParam(value = "job") Optional<Job> job) {
        var employees = job.isPresent() ? employeeUseCase.getAllEmployeesByJob(job.get()) : employeeUseCase.getAllEmployees();
        return new ExchangeDtoWithArg<>("Mitarbeiterliste erfolgreich geladen.", employees);
    }

    @RequestMapping(path = EMPLOYEES_ID_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<Employee> get(@PathVariable("id") Long id) {
        var response = employeeUseCase.getEmployeesById(id);

        return new ExchangeDtoWithArg<>("Mitarbeiter efolgreich geladen.", response.get());
    }

    @RequestMapping(path = EMPLOYEES_ENDPOINT, method = POST)
    public ResponseEntity<ExchangeDto> create(@RequestBody Employee employee, UriComponentsBuilder ucb) {
        var response = new AtomicReference<ResponseEntity<ExchangeDto>>();

        Consumer<Employee> onSuccess = employeeEntity -> {
            var locationUri = ucb.path(EMPLOYEES_ID_ENDPOINT).buildAndExpand(employeeEntity.getId()).toUri();
            String message = String.format("%s %s wurde erfolgreich angelegt.", employeeEntity.getFirstName(), employeeEntity.getLastName());
            var responseEntity = ResponseEntity.created(locationUri).body(new ExchangeDto(message));
            response.set(responseEntity);
        };

        Consumer<String> onError = errorMessage -> {
            var responseEntity = ResponseEntity.badRequest().body(new ExchangeDto(errorMessage));
            response.set(responseEntity);
        };

        employeeUseCase.createEmployee(employee, onSuccess, onError);

        return response.get();
    }

    @RequestMapping(path = EMPLOYEES_ID_ENDPOINT, method = DELETE)
    public ResponseEntity<ExchangeDto> delete(@PathVariable("id") Long id) {
        employeeUseCase.deleteEmployeeById(id);

        return ResponseEntity.ok().body(new ExchangeDto("Mitarbeiter erfolgreich gelöscht."));
    }
}
