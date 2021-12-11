package de.hsesslingen.sa.airport.usecases;

import de.hsesslingen.sa.airport.entities.AirplaneType;
import org.springframework.stereotype.Service;
import de.hsesslingen.sa.airport.interfaceadapters.database.AirplaneTypeRepositiory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AirplaneTypeUseCase {

    private AirplaneTypeRepositiory airplaneTypeRepository;

    public AirplaneTypeUseCase(AirplaneTypeRepositiory airplaneTypeRepository) {
        this.airplaneTypeRepository = airplaneTypeRepository;
    }

    public List<AirplaneType> getAllAirplaneTypes() {
        var airplaneTypes = new ArrayList<AirplaneType>();
        airplaneTypeRepository.findAll().forEach(airplaneTypes::add);
        return airplaneTypes;
    }

    public Optional<AirplaneType> getAirplaneTypeById(Long id) {
        return airplaneTypeRepository.findById(id);
    }

    public void createAirplaneType(AirplaneType airplaneType, Consumer<AirplaneType> onSuccess,
            Consumer<String> onError) {
        IsAirplaneTypOk(airplaneType.getName(),

                () -> IsStartingFeeOk(airplaneType.getStartingFee(), () -> IsLandingFeeOk(airplaneType.getLandingFee(),
                        () -> IsParkingFeeOk(airplaneType.getParkingFee(), () -> {
                            AirplaneType airplaneTypeEntity = airplaneTypeRepository.save(airplaneType);
                            onSuccess.accept(airplaneTypeEntity);
                        },

                                () -> onError.accept("Die Parkgebühren sollen positiv sein")),
                        () -> onError.accept("Die Landegebühren sollen positiv sein")),
                        () -> onError.accept("Die Startgebühren soll positiv sein")),
                () -> onError.accept("Flugzeugtyp soll nicht leer sein"));
    }

    public void IsStartingFeeOk(BigDecimal fee, Runnable onValid, Runnable onInvalid) {
        if (fee.compareTo(BigDecimal.valueOf(0)) > 0) {
            onValid.run();
        } else {
            onInvalid.run();
        }
    }

    public void IsLandingFeeOk(BigDecimal LandingFee, Runnable onValid, Runnable onInvalid) {
        if (LandingFee.signum() > 0) onValid.run();
        else onInvalid.run();

    }

    public void IsAirplaneTypOk(String Name, Runnable onValid, Runnable onInvalid) {
        if (!Name.isBlank()) onValid.run();
        else onInvalid.run();

    }

    public void IsParkingFeeOk(BigDecimal parkingFee, Runnable onValid, Runnable onInvalid) {
        if (parkingFee.signum() > 0) onValid.run();
        else onInvalid.run();

    }

    public void deleteAirplaneTypeById(Long id) {
        airplaneTypeRepository.deleteById(id);
    }
}
