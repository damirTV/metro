package station;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TicketOffice {
    private Map<LocalDate, Long> revenue = new HashMap<>();

    public void saleTicket(LocalDate date, int stages) {
        Long ticketPrice = stages * 5L + 20;
        if (revenue.containsKey(date)) {
            ticketPrice = revenue.get(date) + ticketPrice;
        }
        revenue.put(date, ticketPrice);
    }

    public Map<LocalDate, Long> getRevenue() {
        return revenue;
    }
}
