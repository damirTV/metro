package line;

import metro.Metro;
import station.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Line {
    private final Color color;
    private List<Station> stationList = new ArrayList<>();
    private final Metro metro;

    public Line(Color color, Metro metro) {
        this.color = color;
        this.metro = metro;
    }

    public Color getColor() {
        return color;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public void addStation(String name, Metro metro, Line line) {
        stationList.add(new Station(name, metro, line));
    }

    @Override
    public String toString() {
        return new StringJoiner(",", Line.class.getSimpleName() + "[", "]")
                .add("color='" + color + "'")
                .add("stations='" + stationList + "'").toString();
    }
}
