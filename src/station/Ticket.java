package station;

import java.util.Date;

public class Ticket {
    private double price;
    private Date date;

    public Ticket(double price, Date date) {
        this.price = price;
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
