package de.hsesslingen.sa.airport.interfaceadapters.rest.dto;

public class ExchangeDto {
    private String message;

    public ExchangeDto() {
    }

    public ExchangeDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
