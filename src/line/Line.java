package line;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import errors.Errors;
import metro.Metro;
import station.Station;

public class Line {
    private final Color color;
    private Set<Station> stationList = new LinkedHashSet<>();
    private final Metro metro;

    public Line(Color color, Metro metro) {
        this.color = color;
        this.metro = metro;
    }

    public Color getColor() {
        return color;
    }

    public Set<Station> getStationList() {
        return stationList;
    }

    public Station getLastStation() {
        Station lastStation = null;
        for (Station station : stationList) {
            lastStation = station;
        }
        return lastStation;
    }

    public Set<Station> getChangeStations() {
        Set<Station> changeStationsList = new HashSet<>();
        for (Station station : stationList) {
            if (!station.getStationChangeList().isEmpty()) {
                changeStationsList.add(station);
            }
        }
        return changeStationsList;
    }

    public void checkStationList() {
        if (!this.stationList.isEmpty()) {
            throw new RuntimeException(Errors.LINE_HAVE_STATION.getText());
        }
    }

    public void addStation(String name) {
        stationList.add(new Station(name, this.metro, this));
    }

    @Override
    public String toString() {
        return "Line{color='" + color.getName() + "', stations=" + stationList + "}";
    }
}
