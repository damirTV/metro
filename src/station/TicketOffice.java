package station;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TicketOffice {
    private final Long PRICE_ONE_STAGE = 5L;
    private final Long PRICE_ONE_TICKET = 20L;
    private Map<LocalDate, Long> revenueTotal = new HashMap<>();

    public void saleTicket(LocalDate date, int stages) {
        Long ticketPrice = stages * PRICE_ONE_STAGE + PRICE_ONE_TICKET;
        addRevenue(date, ticketPrice);
    }

    public void salePassMonth(LocalDate date) {
        Long passMonthPrice = 3000L; // Цена абонемента в месяц
        addRevenue(date, passMonthPrice);
    }

    private void addRevenue(LocalDate date, Long revenue) {
        if (revenueTotal.containsKey(date)) {
            revenue = revenueTotal.get(date) + revenue;
        }
        revenueTotal.put(date, revenue);
    }

    public Map<LocalDate, Long> getRevenueTotal() {
        return revenueTotal;
    }
}
