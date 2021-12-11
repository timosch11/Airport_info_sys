package de.hsesslingen.sa.airport.usecases;

import de.hsesslingen.sa.airport.entities.Airline;
import de.hsesslingen.sa.airport.interfaceadapters.database.AirlineRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AirlineUseCase {

    private AirlineRepository airlineRepository;

    public AirlineUseCase(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }



    public List<Airline> getAllAirlines() {
        var airlines = new ArrayList<Airline>();
        airlineRepository.findAll().forEach(airlines::add);
        return airlines;
    }

    public Optional<Airline> getAirlinesById(Long id) {
        return airlineRepository.findById(id);
    }

    public void createAirline(Airline airline, Consumer<Airline> onSuccess, Consumer<String> onError) {
        validateName(airline.getName(), () -> {
            Airline airlineEntity = airlineRepository.save(airline);
            onSuccess.accept(airlineEntity);
        }, () -> onError.accept("Airlinename darf nicht leer sein."));
    }


   public  void validateName(String name, Runnable onValid, Runnable onInvalid) {
        if (name.isBlank()) {
            onInvalid.run();
        } else {
            onValid.run();
        }
    }


    public void deleteAirlineById(Long id) {
        airlineRepository.deleteById(id);
    }
}
