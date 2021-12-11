package de.hsesslingen.sa.airport.usecases;

import de.hsesslingen.sa.airport.entities.Invoice;
import de.hsesslingen.sa.airport.interfaceadapters.database.ScheduleRepository;
import de.hsesslingen.sa.airport.entities.Schedule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

@Service
public class InvoiceUseCase {
    protected Logger LOGGER = Logger.getLogger(InvoiceUseCase.class.getName());

    private ScheduleRepository scheduleRepo;
     
    public InvoiceUseCase(ScheduleRepository scheduleRepository) {
		this.scheduleRepo = scheduleRepository;
	}

    public Collection<Invoice> getAllInvoices() {
		LOGGER.info("*** getAllInvoices() invoked ***");

        var invoices = new ArrayList<Invoice>();
        var schedules = new ArrayList<Schedule>();
        scheduleRepo.findAll().forEach(schedules::add);

        var invoicesMap = new HashMap<String, Invoice>();

        for (Schedule schedule : schedules){
            var date = schedule.getTime().format(DateTimeFormatter.ofPattern("yyyy MM"));
            var key = date + " " + schedule.getAirplane().getAirline().getName();
            if(invoicesMap.containsKey(key)){
                if(schedule.getFlightStatus().toString().equals("LAND")){
                    var invoice = invoicesMap.get(key);
                    invoice.setLandingFee(invoice.getLandingFee().add(schedule.getAirplane().getAirplaneType().getLandingFee()));
                    invoicesMap.put(key, invoice);
                } else {
                    var invoice = invoicesMap.get(key);
                    invoice.setStartingFee(invoice.getStartingFee().add(schedule.getAirplane().getAirplaneType().getStartingFee()));
                    invoice.setParkingFee(invoice.getParkingFee().add(schedule.getAirplane().getAirplaneType().getParkingFee()));
                    invoicesMap.put(key,invoice);
                }
            } else {
                if(schedule.getFlightStatus().toString().equals("LAND")){
                    var invoice = new Invoice();
                    invoice.setAirline(schedule.getAirplane().getAirline());
                    invoice.setLandingFee(schedule.getAirplane().getAirplaneType().getLandingFee());
                    invoice.setParkingFee(new BigDecimal(0));
                    invoice.setStartingFee(new BigDecimal(0));
                    invoice.setDate(schedule.getTime().format(DateTimeFormatter.ofPattern("yyyy MM")));
                    invoicesMap.put(key,invoice);
                } else {
                    var invoice = new Invoice();
                    invoice.setAirline(schedule.getAirplane().getAirline());
                    invoice.setLandingFee(new BigDecimal(0));
                    invoice.setParkingFee(schedule.getAirplane().getAirplaneType().getParkingFee());
                    invoice.setStartingFee(schedule.getAirplane().getAirplaneType().getStartingFee());
                    invoice.setDate(schedule.getTime().format(DateTimeFormatter.ofPattern("yyyy MM")));
                    invoicesMap.put(key,invoice);
                }
            }
        }
        invoicesMap.forEach((k,v) -> {
            invoices.add(v);
        });
        return invoices;
    }

    

    public ArrayList<Invoice> sortInvoicesByAirlineAndDate(Collection<Invoice> invoices) {
        var test = new ArrayList<Invoice>(invoices);

        Collections.sort(test, new AirlineNameSorter()
            .thenComparing(new DateSorter()));

        return test;
    }
    public class AirlineNameSorter implements Comparator<Invoice>
    {
        public int compare(Invoice o1, Invoice o2)
        {
            return o1.getAirline().getName().compareTo(o2.getAirline().getName());
        }
    }
    public class DateSorter implements Comparator<Invoice>
    {
        public int compare(Invoice o1, Invoice o2)
        {
            return o1.getDate().compareTo(o2.getDate());
        }
    }

}
