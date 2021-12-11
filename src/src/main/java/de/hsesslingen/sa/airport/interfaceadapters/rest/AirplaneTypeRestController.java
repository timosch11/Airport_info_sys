package de.hsesslingen.sa.airport.interfaceadapters.rest;

import de.hsesslingen.sa.airport.entities.AirplaneType;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDto;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDtoWithArg;
import de.hsesslingen.sa.airport.usecases.AirplaneTypeUseCase;
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
public class AirplaneTypeRestController extends BaseController {
    private static final String AIRPLANE_TYPES_ENDPOINT = "/api/airplane-types";
    private static final String AIRPLANE_TYPES_ID_ENDPOINT = AIRPLANE_TYPES_ENDPOINT + "/{id}";

    private AirplaneTypeUseCase airplaneTypeUseCase;

    public AirplaneTypeRestController(AirplaneTypeUseCase airplaneTypeUseCase) {
        this.airplaneTypeUseCase = airplaneTypeUseCase;
    }

    @RequestMapping(path = AIRPLANE_TYPES_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<List<AirplaneType>> getAll() {
        var response = airplaneTypeUseCase.getAllAirplaneTypes();

        return new ExchangeDtoWithArg<>("AirplaneTypes loaded", response);
    }

    @RequestMapping(path = AIRPLANE_TYPES_ID_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<AirplaneType> get(@PathVariable("id") Long id) {
        var response = airplaneTypeUseCase.getAirplaneTypeById(id);

        return new ExchangeDtoWithArg<>("AirplaneType loaded", response.get());
    }

    @RequestMapping(path = AIRPLANE_TYPES_ENDPOINT, method = POST)
    public ResponseEntity<ExchangeDto> create(@Valid @RequestBody AirplaneType airplaneType, UriComponentsBuilder ucb) {
        var response = new AtomicReference<ResponseEntity<ExchangeDto>>();

        Consumer<AirplaneType> onSuccess = airplaneTypeEntity -> {
            var locationUri = ucb.path(AIRPLANE_TYPES_ID_ENDPOINT).buildAndExpand(airplaneTypeEntity.getId()).toUri();
            String message = String.format("%s wurde erfolgreich angelegt.", airplaneType.getName());
            var responseEntity = ResponseEntity.created(locationUri).body(new ExchangeDto(message));
            response.set(responseEntity);
        };

        Consumer<String> onError = errorMessage -> {
            var responseEntity = ResponseEntity.badRequest().body(new ExchangeDto(errorMessage));
            response.set(responseEntity);
        };

        airplaneTypeUseCase.createAirplaneType(airplaneType, onSuccess, onError);

        return response.get();
    }

    @RequestMapping(path = AIRPLANE_TYPES_ID_ENDPOINT, method = DELETE)
    public ResponseEntity<ExchangeDto> delete(@PathVariable("id") Long id) {
        airplaneTypeUseCase.deleteAirplaneTypeById(id);

        return ResponseEntity.ok().body(new ExchangeDto("AirplaneType deleted"));
    }
}
