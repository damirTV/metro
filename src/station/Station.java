package station;

import line.Line;
import metro.Metro;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Station {
    private String name;
    private Station prevStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private Line line;
    private List<Station> stationChangeList;
    private Metro metro;
    private TicketOffice ticketOffice;

    public Station(String name, Metro metro, Line line) {
        this.name = name;
        this.metro = metro;
        this.line = line;
        stationChangeList = new ArrayList<>();
        this.ticketOffice = new TicketOffice();
    }

    public String getName() {
        return name;
    }

    public List<Station> getStationChangeList() {
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

    @Override
    public String toString() {
        List<String> changeList = new ArrayList<>();
        if (stationChangeList.isEmpty()) {
            changeList.add("null");
        } else {
            for (Station station : stationChangeList) {
                changeList.add(station.getLine().getColor().getName());
            }
        }
        return new StringJoiner(",", Station.class.getSimpleName() + "{", "}")
                .add("name='" + name + "'")
                .add("changeLines=" + changeList).toString();
    }
}
