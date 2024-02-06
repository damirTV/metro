package station;

import errors.Errors;
import metro.PassMonth;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class TicketOffice {
    private final BigDecimal PRICE_ONE_STAGE = new BigDecimal("5");
    private final BigDecimal PRICE_ONE_TICKET = new BigDecimal("20");
    private Map<LocalDate, BigDecimal> revenue = new HashMap<>();
    private final BigDecimal passMonthPrice = new BigDecimal("3000");

    public void saleTicket(LocalDate date, int stages) {
        BigDecimal ticketPrice = PRICE_ONE_STAGE.multiply(new BigDecimal(stages)).add(PRICE_ONE_TICKET);
        addRevenue(date, ticketPrice);
    }

    public void salePassMonth(LocalDate date) {
        addRevenue(date, passMonthPrice);
    }

    public void renewPassMonth(List<PassMonth> passMonths, String number, LocalDate date) {
        Optional<PassMonth> passMonth = Optional.ofNullable(passMonths.stream()
                .filter(value -> number.equals(value.getNumber()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(Errors.PASS_MONTH_NOT_EXISTS.getText())));
        passMonth.ifPresent(value -> value.setExpireDate(date.plusMonths(1)));
        addRevenue(date, passMonthPrice);
    }

    private void addRevenue(LocalDate date, BigDecimal revenueAdd) {
        if (revenue.containsKey(date)) {
            revenueAdd = revenueAdd.add(revenue.get(date));
        }
        revenue.put(date, revenueAdd);
    }

    public Map<LocalDate, BigDecimal> getRevenue() {
        return revenue;
    }
}
