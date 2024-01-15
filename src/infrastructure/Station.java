package infrastructure;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Station {
    private String name;
    private Station prevStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private Line line;
    private List<Station> stationChangeList = new ArrayList<>();
    private Metro metro;

    public Station(String name, Metro metro, Line line) {
        this.name = name;
        this.metro = metro;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setStationChangeList(Station stations) {
        stationChangeList.add(stations);
    }

    public void setPrevStation(Station prevStation) {
        this.prevStation = prevStation;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public void setTimeToNextStation(Duration timeToNextStation) {
        this.timeToNextStation = timeToNextStation;
    }
}
