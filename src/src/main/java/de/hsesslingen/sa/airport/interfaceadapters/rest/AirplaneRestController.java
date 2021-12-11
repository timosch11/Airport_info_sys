package de.hsesslingen.sa.airport.interfaceadapters.rest;

import de.hsesslingen.sa.airport.entities.Airplane;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDto;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDtoWithArg;
import de.hsesslingen.sa.airport.usecases.AirplaneUseCase;
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
public class AirplaneRestController extends BaseController {
    private static final String AIRPLANES_ENDPOINT = "/api/airplanes";
    private static final String AIRPLANES_ID_ENDPOINT = AIRPLANES_ENDPOINT + "/{id}";

    private AirplaneUseCase airplaneUseCase;

    public AirplaneRestController(AirplaneUseCase airplaneUseCase) {
        this.airplaneUseCase = airplaneUseCase;
    }

    @RequestMapping(path = AIRPLANES_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<List<Airplane>> getAll() {
        var response = airplaneUseCase.getAllAirplanes();

        return new ExchangeDtoWithArg<>("Airplanes loaded", response);
    }

    @RequestMapping(path = AIRPLANES_ID_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<Airplane> get(@PathVariable("id") Long id) {
        var response = airplaneUseCase.getAirplaneById(id);

        return new ExchangeDtoWithArg<>("Airplane loaded", response.get());
    }

    @RequestMapping(path = AIRPLANES_ENDPOINT, method = POST)
    public ResponseEntity<ExchangeDto> create(@Valid @RequestBody Airplane airplane, UriComponentsBuilder ucb) {
        var response = new AtomicReference<ResponseEntity<ExchangeDto>>();

        Consumer<Airplane> onSuccess = airplaneEntity -> {
            var locationUri = ucb.path(AIRPLANES_ID_ENDPOINT).buildAndExpand(airplaneEntity.getId()).toUri();
            String message = String.format("%s wurde erfolgreich angelegt.", airplaneEntity.getRegistration());
            var responseEntity = ResponseEntity.created(locationUri).body(new ExchangeDto(message));
            response.set(responseEntity);
        };

        Consumer<String> onError = errorMessage -> {
            var responseEntity = ResponseEntity.badRequest().body(new ExchangeDto(errorMessage));
            response.set(responseEntity);
        };

        airplaneUseCase.createAirplane(airplane, onSuccess, onError);

        return response.get();
    }

    @RequestMapping(path = AIRPLANES_ID_ENDPOINT, method = DELETE)
    public ResponseEntity<ExchangeDto> delete(@PathVariable("id") Long id) {
        airplaneUseCase.deleteAirplaneById(id);

        return ResponseEntity.ok().body(new ExchangeDto("Airplane deleted"));
    }
}
