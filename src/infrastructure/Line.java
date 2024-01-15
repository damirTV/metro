package infrastructure;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private Color color;
    private List<Station> stationList = new ArrayList<>();
    private final Metro metro;

    public Line(Color color, Metro metro) {
        this.color = color;
        this.metro = metro;
    }

    public Color getColor() {
        return color;
    }

    public void addStation(String name, Metro metro, Line line) {
        stationList.add(new Station(name, metro, line));
    }

    public List<Station> getStationList() {
        return stationList;
    }
}
