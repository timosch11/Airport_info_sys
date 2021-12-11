package de.hsesslingen.sa.airport.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class AirplaneType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private BigDecimal startingFee;

    private BigDecimal landingFee;

    private BigDecimal parkingFee;

    public AirplaneType() {
    }

    public AirplaneType(String name, BigDecimal startingFee, BigDecimal landingFee, BigDecimal parkingFee) {
        this.name = name;
        this.startingFee = startingFee;
        this.landingFee = landingFee;
        this.parkingFee = parkingFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getStartingFee() {
        return startingFee;
    }

    public void setStartingFee(BigDecimal startingFee) {
        this.startingFee = startingFee;
    }

    public BigDecimal getLandingFee() {
        return landingFee;
    }

    public void setLandingFee(BigDecimal landingFee) {
        this.landingFee = landingFee;
    }

    public BigDecimal getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(BigDecimal parkingFee) {
        this.parkingFee = parkingFee;
    }
}
