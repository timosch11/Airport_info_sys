package de.hsesslingen.sa.airport.usecases;
import de.hsesslingen.sa.airport.AirportApplication;
import de.hsesslingen.sa.airport.entities.Airplane;
import de.hsesslingen.sa.airport.entities.FlightStatus;
import de.hsesslingen.sa.airport.entities.Schedule;
import de.hsesslingen.sa.airport.interfaceadapters.database.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.ArrayList;

@Service
public class ScheduleUseCase {

    protected Logger LOGGER = Logger.getLogger(ScheduleUseCase.class.getName());

	private ScheduleRepository scheduleRepo;
	@Autowired
	private Environment env;

	public ScheduleUseCase(ScheduleRepository scheduleRepository) {
		this.scheduleRepo = scheduleRepository;
	}
	

    public List<Schedule> getSchedules(Optional<FlightStatus> flightStatus) {
        LOGGER.info("*** getSchedules() invoked ***");
		var schedules = new ArrayList<Schedule>();
		if (flightStatus.isPresent()) {
			LOGGER.info("*** flight status is present ***");
			if (flightStatus.toString().equals("Optional[LAND]")) {
				LOGGER.info("*** flight status:: LANDING ***");
				for (Schedule s : scheduleRepo.findAll()) {
					if (s.getFlightStatus().toString().equals("LAND")) {
						schedules.add(s);
					}
				}
			} else if (flightStatus.toString().equals("Optional[TAKE_OFF]")) {
				LOGGER.info("*** flight status:: TAKE-OFF ***");
				for (Schedule s : scheduleRepo.findAll()) {
					if (s.getFlightStatus().toString().equals("TAKE_OFF")) {
						schedules.add(s);
					}
				}
			} else {
				scheduleRepo.findAll().forEach(schedules::add);
			}
		}
		LOGGER.info("*** Schedules::" + schedules + " ***");
		return schedules;
	}

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepo.findById(id);
    }

    public void createSchedule(Schedule schedule, Consumer<Schedule> onSuccess, Consumer<String> onError) {
        LOGGER.info("*** makeNewSchedule() invoked ***");
		
		validateAirplane(schedule.getAirplane(),
				() -> validateRunway(schedule.getRunway(), () -> validateTime(schedule.getTime(),
						() -> checkAndValidateFlightAvailablity(schedule.getTime(), schedule.getAirplane(), () -> {
							Schedule scheduleEntity = scheduleRepo.save(schedule);
							onSuccess.accept(scheduleEntity);
						}, () -> onError.accept("Zeitfenster und Landebahn nicht vereinbar.")),
						() -> onError.accept("Zeitfenster muss bekannt sein.")),
						() -> onError.accept("Landebahn nicht verfügbar.")),
				() -> onError.accept("Flugzeug muss bekannt sein."));
	}
    void validateAirplane(Airplane airplane, Runnable onKnown, Runnable onUnknown) {
		LOGGER.info("*** validateAirplane () invoked ***");
		if (airplane.getId() != null) {
			onKnown.run();
		} else {
			onUnknown.run();
		}
	}
   void validateRunway(Integer runway, Runnable onKnown, Runnable onUnknown) {
		LOGGER.info("*** validateRunway invoked()  ***");
		String runWayCount = env.getProperty("airport.runway-count");
		if (runWayCount != null) {
			LOGGER.info("*** airport.runway-count= " + runWayCount + " ***");
			int airportRunWayCountValue = Integer.parseInt(runWayCount);
			if (runway <= airportRunWayCountValue && runway != 0) {
				onKnown.run();
			} else {
				onUnknown.run();
			}
		}

	}
	
	

    void validateTime(LocalDateTime time, Runnable onValid, Runnable onInvalid) {
		LOGGER.info("*** validateTime () invoked***");
		
		if (time.isAfter(LocalDateTime.now())) {
			onValid.run();
		} else {
			onInvalid.run();
		}
	}

    private void checkAndValidateFlightAvailablity(LocalDateTime time, Airplane airplane, Runnable onValid,
			Runnable onInvalid) {
		LOGGER.info("*** checkAndValidateFlightAvailability() invoked ***");
		var schedules = new ArrayList<Schedule>();
		scheduleRepo.findAll().forEach(schedules::add);

		for (Schedule schedule : schedules) {
			

			if (schedule.getAirplane().getId().equals(airplane.getId())) {

				if (schedule.getTime().isAfter(time)){
					System.out.print("Inner if ");
					
				onInvalid.run();
				return;
				
			}}
			System.out.println(schedule.getAirplane().getId());
			System.out.println(airplane.getId());
			System.out.println(time);
			System.out.println(schedule.getTime().plusMinutes(40));
			if (schedule.getAirplane().getId().equals(airplane.getId()) &&
			time.isEqual(schedule.getTime().plusMinutes(40))) {
				System.out.print("Inner if");
				onInvalid.run();
				return;
		
		
			
		}}
		onValid.run();
	}

    public void deleteScheduleById(Long id) {
        LOGGER.info("*** deleteScheduleById invoked***");
		scheduleRepo.deleteById(id);
    }

    public List<Integer> getAvailableRunways(LocalDateTime time, Integer duration) {
        LOGGER.info("*** getAllAvailableRunways() invoked ***");
		var schedules = new ArrayList<Schedule>();
		var runways = new ArrayList<Integer>();
		scheduleRepo.findAll().forEach(schedules::add);
		String runWayCount = env.getProperty("airport.runway-count");
		int airportRunWayCountValue = 0;
		if (runWayCount != null) {

			airportRunWayCountValue = Integer.parseInt(runWayCount);

		}
		for (int i = 1; i <= airportRunWayCountValue; i++) {
			runways.add(i);
		}

		for (Schedule schedule : schedules) {
			if (!(schedule.getTime().plusMinutes(schedule.getDuration()).isBefore(time)
					|| schedule.getTime().isAfter(time.plusMinutes(duration)))) {
				runways.remove(schedule.getRunway());
			}
		}
		LOGGER.info("*** All available runways " + runways + " ***");
		return runways;
	}
}