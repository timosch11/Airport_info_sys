package de.hsesslingen.sa.airport.interfaceadapters.rest;

import de.hsesslingen.sa.airport.entities.Invoice;
import de.hsesslingen.sa.airport.interfaceadapters.rest.dto.ExchangeDtoWithArg;
import de.hsesslingen.sa.airport.usecases.InvoiceUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class InvoiceRestController extends BaseController {
    private static final String INVOICES_ENDPOINT = "/api/invoices";

    private InvoiceUseCase invoiceUseCase;

    public InvoiceRestController(InvoiceUseCase invoiceUseCase) {
        this.invoiceUseCase = invoiceUseCase;
    }

    @RequestMapping(path = INVOICES_ENDPOINT, method = GET)
    public ExchangeDtoWithArg<Collection<Invoice>> getAll() {
        var invoices = invoiceUseCase.getAllInvoices();
        var sorted = invoiceUseCase.sortInvoicesByAirlineAndDate(invoices);
        return new ExchangeDtoWithArg<>("Invoices loaded", sorted);
    }

}
