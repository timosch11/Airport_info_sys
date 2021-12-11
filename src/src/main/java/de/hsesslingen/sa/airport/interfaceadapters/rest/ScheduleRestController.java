package de.hsesslingen.sa.airport.interfaceadapters.rest;

import de.hsesslingen.sa.airport.entities.FlightStatus;
import de.hsesslingen.sa.airport.entities.Schedule;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDto;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDtoWithArg;
import de.hsesslingen.sa.airport.usecases.ScheduleUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ScheduleRestController extends BaseController {

    private static final String FREE_RUNWAYS_ENDPOINT = "/api/schedules/free-runways";
    private static final String SCHEDULES_ENDPOINT = "/api/schedules";
    private static final String SCHEDULES_ID_ENDPOINT = SCHEDULES_ENDPOINT + "/{id}";

    private ScheduleUseCase scheduleUseCase;

    public ScheduleRestController(ScheduleUseCase scheduleUseCase) {
        this.scheduleUseCase = scheduleUseCase;
    }

    @RequestMapping(path = SCHEDULES_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<List<Schedule>> getAll(@RequestParam(value = "flight-status") Optional<FlightStatus> flightStatus) {
        var schedules = scheduleUseCase.getSchedules(flightStatus);

        return new ExchangeDtoWithArg<>("Schedules loaded", schedules);
    }

    @RequestMapping(path = SCHEDULES_ID_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<Schedule> get(@PathVariable("id") Long id) {
        var response = scheduleUseCase.getScheduleById(id);

        return new ExchangeDtoWithArg<>("Schedule loaded", response.get());
    }

    @RequestMapping(path = SCHEDULES_ENDPOINT, method = POST)
    public ResponseEntity<ExchangeDto> create(@Valid @RequestBody Schedule schedule, UriComponentsBuilder ucb) {

        var response = new AtomicReference<ResponseEntity<ExchangeDto>>();

        Consumer<Schedule> onSuccess = scheduleEntity -> {
            var locationUri = ucb.path(SCHEDULES_ID_ENDPOINT).buildAndExpand(scheduleEntity.getId()).toUri();
            String message = String.format("Planung wurde erfolgreich angelegt.");
            var responseEntity = ResponseEntity.created(locationUri).body(new ExchangeDto(message));
            response.set(responseEntity);
        };

        Consumer<String> onError = errorMessage -> {
            var responseEntity = ResponseEntity.badRequest().body(new ExchangeDto(errorMessage));
            response.set(responseEntity);
        };

        scheduleUseCase.createSchedule(schedule, onSuccess, onError);

        return response.get();
    }

    @RequestMapping(path = SCHEDULES_ID_ENDPOINT, method = DELETE)
    public ResponseEntity<ExchangeDto> delete(@PathVariable("id") Long id) {
        scheduleUseCase.deleteScheduleById(id);

        return ResponseEntity.ok().body(new ExchangeDto("Schedule deleted"));
    }

    @RequestMapping(path = FREE_RUNWAYS_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<List<Integer>> getFreeRunways(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time, @RequestParam Integer duration) {
        var availableRunways = scheduleUseCase.getAvailableRunways(time, duration);
        return new ExchangeDtoWithArg<>("Runways loaded", availableRunways);
    }

}
