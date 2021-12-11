package de.hsesslingen.sa.airport.entities;

import java.math.BigDecimal;

public class Invoice {

    private Airline airline;

    private BigDecimal startingFee;

    private BigDecimal landingFee;

    private BigDecimal parkingFee;

    private String date; // shall be formatted by yyyy-MM

    public Invoice() {
    }

    public Invoice(Airline airline, BigDecimal startingFee, BigDecimal landingFee, BigDecimal parkingFee, String date) {
        this.airline = airline;
        this.startingFee = startingFee;
        this.landingFee = landingFee;
        this.parkingFee = parkingFee;
        this.date = date;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
