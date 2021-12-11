package de.hsesslingen.sa.airport.usecases;

import de.hsesslingen.sa.airport.entities.Airline;
import de.hsesslingen.sa.airport.entities.Airplane;
import de.hsesslingen.sa.airport.entities.AirplaneType;
import de.hsesslingen.sa.airport.interfaceadapters.database.AirlineRepository;
import de.hsesslingen.sa.airport.interfaceadapters.database.AirplaneRepository;
import de.hsesslingen.sa.airport.interfaceadapters.database.AirplaneTypeRepositiory;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AirplaneUseCase {
    public AirplaneRepository airplaneRepository;
    public AirlineRepository airlineRepository;
   public  AirplaneTypeRepositiory airplaneTypeRepository;

   public AirplaneUseCase(AirplaneRepository airplaneRepository,AirlineRepository airlineRepository,AirplaneTypeRepositiory airplaneTypeRepository) {
    this.airplaneRepository = airplaneRepository;
    this.airlineRepository = airlineRepository;
    this.airplaneTypeRepository = airplaneTypeRepository;

}

    public List<Airplane> getAllAirplanes() {
        var airplanes = new ArrayList<Airplane>();
        airplaneRepository.findAll().forEach(airplanes::add);
        return airplanes;
    }

    public Optional<Airplane> getAirplaneById(Long id) {
        return airplaneRepository.findById(id);
    }

    public void createAirplane(Airplane airplane, Consumer<Airplane> onSuccess, Consumer<String> onError) {
        {
            ValAirline(airplane.getAirline(),
                () -> ValAirplaneType(airplane.getAirplaneType(), () -> ValRegistration(airplane.getRegistration(),
                        () -> {
                            Airplane airplaneEntity = airplaneRepository.save(airplane);
                            onSuccess.accept(airplaneEntity);
                        },
                        () -> onError.accept("Kennzeichen ist nicht korrekt")),
                    () -> onError.accept("Airplane Typ ist nicht korrekt")),
                () -> onError.accept("Airline ist nicht korrekt"));
        }
    }
    public void ValRegistration(String registration, Runnable onValid, Runnable onInvalid) {
        
        Pattern p = Pattern.compile("^[a-zA-Z]{2}-[a-zA-Z0-9_]{4}$");
        Matcher m = p.matcher(registration);
        if(m.matches()) onValid.run();
        else  onInvalid.run();
    }
    
    public void ValAirline(Airline airline, Runnable onValid, Runnable onInvalid) {
        if (airlineRepository.existsById(airline.getId())) onValid.run();
        else onInvalid.run();
        
    }
   public  void ValAirplaneType(AirplaneType airplaneType, Runnable onValid, Runnable onInvalid) {
        if (airplaneTypeRepository.existsById(airplaneType.getId())) onValid.run();
         else onInvalid.run();
        
    }


    public void deleteAirplaneById(Long id) {
        airplaneRepository.deleteById(id);
    }
}
