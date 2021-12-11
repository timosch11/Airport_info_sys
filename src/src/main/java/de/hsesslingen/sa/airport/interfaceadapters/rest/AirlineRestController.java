package de.hsesslingen.sa.airport.interfaceadapters.rest;

import de.hsesslingen.sa.airport.entities.Airline;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDto;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDtoWithArg;
import de.hsesslingen.sa.airport.usecases.AirlineUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class AirlineRestController extends BaseController {
    private static final String AIRLINES_ENDPOINT = "/api/airlines";
    private static final String AIRLINES_ID_ENDPOINT = AIRLINES_ENDPOINT + "/{id}";

    private AirlineUseCase airlineUseCase;

    public AirlineRestController(AirlineUseCase airlineUseCase) {
        this.airlineUseCase = airlineUseCase;
    }

    @RequestMapping(path = AIRLINES_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<List<Airline>> getAll() {
        var response = airlineUseCase.getAllAirlines();

        return new ExchangeDtoWithArg<>("Airlines loaded", response);
    }

    @RequestMapping(path = AIRLINES_ID_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<Airline> get(@PathVariable("id") Long id) {
        var response = airlineUseCase.getAirlinesById(id);

        return new ExchangeDtoWithArg<>("Airline loaded", response.get());
    }

    @RequestMapping(path = AIRLINES_ENDPOINT, method = POST)
    public ResponseEntity<ExchangeDto> create(@Valid @RequestBody Airline airline, UriComponentsBuilder ucb) {
        var response = new AtomicReference<ResponseEntity<ExchangeDto>>();

        Consumer<Airline> onSuccess = airlineEntity -> {
            var locationUri = ucb.path(AIRLINES_ID_ENDPOINT).buildAndExpand(airlineEntity.getId()).toUri();
            String message = String.format("%s wurde erfolgreich angelegt.", airlineEntity.getName());
            var responseEntity = ResponseEntity.created(locationUri).body(new ExchangeDto(message));
            response.set(responseEntity);
        };

        Consumer<String> onError = errorMessage -> {
            var responseEntity = ResponseEntity.badRequest().body(new ExchangeDto(errorMessage));
            response.set(responseEntity);
        };

        airlineUseCase.createAirline(airline, onSuccess, onError);

        return response.get();
    }

    @RequestMapping(path = AIRLINES_ID_ENDPOINT, method = DELETE)
    public ResponseEntity<ExchangeDto> delete(@PathVariable("id") Long id) {
        airlineUseCase.deleteAirlineById(id);
        return ResponseEntity.ok().body(new ExchangeDto("Airline deleted"));
    }
}
