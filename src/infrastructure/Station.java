package infrastructure;

import java.time.Duration;
import java.util.List;

public class Station {
    private String name;
    private Station prevStation;
    private Station nextStation;
    private Duration timeToNextStation;
    private Line line;
    private List<Station> stationTransferList;
    private Metro metro;

    public Station(String name, Metro metro, Line line) {
        this.name = name;
        this.metro = metro;
        this.line = line;
    }

    public String getName() {
        return name;
    }
}
