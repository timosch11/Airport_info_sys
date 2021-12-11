package de.hsesslingen.sa.airport.entities;

import javax.persistence.*;

@Entity
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String registration;

    @ManyToOne
    private Airline airline;

    @ManyToOne
    private AirplaneType airplaneType;

    public Airplane() {
    }

    public Airplane(String registration, Airline airline, AirplaneType airplaneType) {
        this.registration = registration;
        this.airline = airline;
        this.airplaneType = airplaneType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public AirplaneType getAirplaneType() {
        return airplaneType;
    }

    public void setAirplaneType(AirplaneType airplaneType) {
        this.airplaneType = airplaneType;
    }
}
