package de.hsesslingen.sa.airport.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Airplane airplane;

    private Integer runway;

    private LocalDateTime time;

    private Integer duration;

    @ManyToOne
    private Employee controller;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private FlightStatus flightStatus;

    public Schedule() {
    }

    public Schedule(Airplane airplane, Integer runway, LocalDateTime time, Integer duration, Employee controller, FlightStatus flightStatus) {
        this.airplane = airplane;
        this.runway = runway;
        this.time = time;
        this.duration = duration;
        this.controller = controller;
        this.flightStatus = flightStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Integer getRunway() {
        return runway;
    }

    public void setRunway(Integer runway) {
        this.runway = runway;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Employee getController() {
        return controller;
    }

    public void setController(Employee controller) {
        this.controller = controller;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }
}
