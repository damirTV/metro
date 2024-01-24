package station;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TicketOffice {
    private List<Ticket> ticketList = new ArrayList<>();

    public void saleTicket(Date date, String firstStation, String lastStation) {
        ticketList.add(new Ticket(10, date, firstStation, lastStation));
    }

    public List<Ticket> listTickets() {
        return ticketList;
    }
}
