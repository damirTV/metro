package station;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        return ticketOffice.getRevenue();
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

    private List<String> convertNullToString() {
        List<String> changeList = new ArrayList<>();
        if (stationChangeList.isEmpty()) {
            changeList.add("null");
        } else {
            for (Station station : stationChangeList) {
                changeList.add(station.getLine().getColor().getName());
            }
        }
        return changeList;
    }

    public void saleTicket(LocalDate date, String firstStation, String lastStation, int stages) {
        ticketOffice.saleTicket(date, firstStation, lastStation, stages);
    }

    @Override
    public String toString() {
        List<String> changeList = convertNullToString();
        return "Station{name='" + name + "', changeLines=" + changeList + "}";
    }
}
