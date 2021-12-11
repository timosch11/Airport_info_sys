package de.hsesslingen.sa.airport.usecases;

import java.util.Comparator;

import de.hsesslingen.sa.airport.entities.Invoice;

public class DateSorter implements Comparator<Invoice>
    {
        public int compare(Invoice o1, Invoice o2)
        {
            return o1.getDate().compareTo(o2.getDate());
        }
    }