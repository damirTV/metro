package line;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import errors.Errors;
import metro.Metro;
import station.Station;

public class Line {
    private final Color color;
    private Set<Station> stations = new LinkedHashSet<>();
    private final Metro metro;

    public Line(Color color, Metro metro) {
        this.color = color;
        this.metro = metro;
    }

    public Color getColor() {
        return color;
    }

    public Set<Station> getStations() {
        return stations;
    }

    public Station getLastStation() {
        ArrayList<Station> lastStation = new ArrayList<>(stations);
        return lastStation.get(lastStation.size() - 1);
    }

    public Set<Station> getChangeStations() {
        return stations.stream()
                .filter(value -> !value.getStationChangeList().isEmpty())
                .collect(Collectors.toSet());
    }

    public void checkStationList() {
        if (!this.stations.isEmpty()) {
            throw new RuntimeException(Errors.LINE_HAVE_STATION.getText());
        }
    }

    public void addStation(String name) {
        stations.add(new Station(name, this.metro, this));
    }

    @Override
    public String toString() {
        return "Line{color='" + color.getName() + "', stations=" + stations + "}";
    }
}
