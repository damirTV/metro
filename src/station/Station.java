package station;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import line.Line;
import metro.Metro;

public class Station {
    private String name;
    private Station prevStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private Line line;
    private Set<Station> stationChangeList = new HashSet<>();
    private Metro metro;
    private TicketOffice ticketOffice = new TicketOffice();

    public Station(String name, Metro metro, Line line) {
        this.name = name;
        this.metro = metro;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public Set<Station> getStationChangeList() {
        return stationChangeList;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public Station getPrevStation() {
        return prevStation;
    }

    public Line getLine() {
        return line;
    }

    public Map<LocalDate, Long> getRevenue() {
        return ticketOffice.getRevenueTotal();
    }

    public void setStationChangeList(Station stations) {
        stationChangeList.add(stations);
    }

    public void setPrevStation(Station prevStation) {
        this.prevStation = prevStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public void setTimeToNextStation(Duration timeToNextStation) {
        this.timeToNextStation = timeToNextStation;
    }

    private String getChangeLines() {
        if (stationChangeList.isEmpty()) {
            return null;
        }
        StringBuilder stationChangesList = new StringBuilder();
        for (Station station : stationChangeList) {
            stationChangesList.append(station.getLine().getColor().getName());
        }
        return stationChangesList.toString();
    }

    public void saleTicket(LocalDate date, int stages) {
        ticketOffice.saleTicket(date, stages);
    }

    public void salePassMonth(LocalDate date) {
        ticketOffice.salePassMonth(date);
    }

    @Override
    public String toString() {
        return "Station{name='" + name + "', changeLines=" + getChangeLines() + "}";
    }
}
