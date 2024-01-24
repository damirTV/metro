package station;

import java.util.Date;

public class Ticket { // TODO - не по ТЗ. Переделать в private Map<LocalDate, Long>
    private double price;
    private Date date;
    private String firstStation;
    private String lastStation;

    public Ticket(double price, Date date, String firstStation, String lastStation) {
        this.price = price;
        this.date = date;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public String getFirstStation() {
        return firstStation;
    }

    public String getLastStation() {
        return lastStation;
    }
}
