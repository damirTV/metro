package infrastructure;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Metro {
    private final String city;
    private List<Line> lineList;

    public Metro(String city) {
        this.city = city;
        this.lineList = new ArrayList<>();
    }

    private Station getStationByName(String name) {
        for (int i = 0; i < lineList.size(); i++) {
            for (int j = 0; j < lineList.get(i).getStationList().size(); j++) {
                if (Objects.equals(name, lineList.get(i).getStationList().get(j).getName())) {
                    return lineList.get(i).getStationList().get(j);
                }
            }
        }
        throw new RuntimeException(Errors.E01.getText());
    }

    private Station getLastStation(Color color) {
        if ((findLineByColor(color).getStationList().get(findLineByColor((color))
                .getStationList().size() - 1) == null)) {
            throw new RuntimeException(Errors.E04.getText());
        }
        return findLineByColor(color).getStationList().get(findLineByColor((color))
                .getStationList().size() - 1);
    }

    public List<Line> getLineList() {
        return lineList;
    }

    public int getNumberStagesBetweenStations(Station firststation, Station lastStation) {
        if (getNumberStagesBetweenNextStations(firststation, lastStation) != -1) {
            return getNumberStagesBetweenNextStations(firststation, lastStation);
        }
        if (getNumberStagesBetweenPrevStations(firststation, lastStation) != -1) {
            return getNumberStagesBetweenPrevStations(firststation, lastStation);
        }
        throw new RuntimeException(Errors.E11.getText() + " " + firststation + " в " + lastStation);
    }

    private int getNumberStagesBetweenNextStations(Station firstStation, Station lastStation) {
        if (firstStation.getLine() != lastStation.getLine()) {
            throw new RuntimeException(Errors.E06.getText());
        }
        int countStage = 1;
        while (firstStation.getNextStation() != lastStation) {
            if (firstStation.getNextStation() == null) {
                return -1;
            }
            firstStation = firstStation.getNextStation();
            countStage++;
        }
        return countStage;
    }

    private int getNumberStagesBetweenPrevStations(Station firstStation, Station lastStation) {
        if (firstStation.getLine() != lastStation.getLine()) {
            throw new RuntimeException(Errors.E06.getText());
        }
        int countStage = 1;
        while (firstStation.getPrevStation() != lastStation) {
            if (firstStation.getPrevStation() == null) {
                return -1;
            }
            firstStation = firstStation.getPrevStation();
            countStage++;
        }
        return countStage;
    }

    private void setStationChangeList(Station lastStation, List<String> changeStations) {
        for (String station : changeStations) {
            Station changeStation = getStationByName(station);
            changeStation.setStationChangeList(lastStation);
            lastStation.setStationChangeList(changeStation);
        }
    }

    public Station findChangeStation(Line lineFrom, Line lineTo) {
        for (int i = 0; i < lineFrom.getStationList().size(); i++) {
            List<Station> changeStations = lineFrom.getStationList().get(i).getStationChangeList();
            if (!changeStations.isEmpty()) {
                for (Station changeStation : changeStations) {
                    if (changeStation.getLine() == lineTo) {
                        return lineFrom.getStationList().get(i);
                    }
                }
            }
        }
        throw new RuntimeException(Errors.E10.getText());
    }

    public void createNewLine(Color color, Metro metro) {
        for (Line line : lineList) {
            if (line.getColor() == color) {
                throw new RuntimeException(Errors.E09.getText());
            }
        }
        lineList.add(new Line(color, metro));
    }

    public void createFirstStationLine(Color color, String nameStation, Metro metro) {
        checksForFirstStation(color, nameStation);
        findLineByColor(color).addStation(nameStation, metro, findLineByColor(color));
    }

    public void createFirstStationLine(Color color, String nameStation, Metro metro,
                                       List<String> changeStations) {
        checksForFirstStation(color, nameStation);
        findLineByColor(color).addStation(nameStation, metro, findLineByColor(color));
        setStationChangeList(getLastStation(color), changeStations);

    }

    public void createLastStationLine(Color color, String nameStation, Metro metro,
                                      Duration duration) {
        Station prevStation = getLastStation(color);
        checksForLastStation(color, nameStation, prevStation, duration);
        commonCreateLastStationLine(duration, color, nameStation, metro,
                findLineByColor(color), prevStation);
    }

    public void createLastStationLine(Color color, String nameStation, Metro metro,
                                      Duration duration, List<String> changeStations) {
        Station prevStation = getLastStation(color);
        checksForLastStation(color, nameStation, prevStation, duration);
        commonCreateLastStationLine(duration, color, nameStation, metro,
                findLineByColor(color), prevStation);
        setStationChangeList(getLastStation(color), changeStations);
    }

    private void commonCreateLastStationLine(Duration duration, Color color, String nameStation,
                                              Metro metro, Line line, Station prevStation) {
        prevStation.setTimeToNextStation(duration);
        findLineByColor(color).addStation(nameStation, metro, findLineByColor(color));
        Station lastStation = getLastStation(color);
        prevStation.setNextStation(lastStation);
        lastStation.setPrevStation(prevStation);
    }

    private Line findLineByColor(Color color) {
        for (Line line : lineList) {
            if (line.getColor() == color) {
                return line;
            }
        }
        throw new RuntimeException(Errors.E05.getText());
    }

    private void checksForFirstStation(Color color, String nameStation) {
        checkLineColor(color);
        checkNameStation(nameStation);
        checkLineHaveStation(color);
    }

    private void checksForLastStation(Color color, String nameStation,
                                      Station prevStation, Duration duration) {
        checkLineColor(color);
        checkNameStation(nameStation);
        checkStationHaveNextStation(prevStation);
        checkTimeToNextStation(duration);
    }

    private void checkTimeToNextStation(Duration duration) {
        if (duration == Duration.ZERO) {
            throw new RuntimeException(Errors.E02.getText());
        }
    }

    private void checkStationHaveNextStation(Station station) {
        if (station.getNextStation() != null) {
            throw new RuntimeException(Errors.E03.getText());
        }
    }


    private void checkLineColor(Color color) {
        for (Line line : lineList) {
            if (line.getColor() == color) {
                return;
            }
        }
        throw new RuntimeException(Errors.E05.getText());
    }

    private void checkNameStation(String name) {
        for (int i = 0; i < lineList.size(); i++) {
            for (int j = 0; j < lineList.get(i).getStationList().size(); j++) {
                if (Objects.equals(name, lineList.get(i).getStationList().get(j).getName())) {
                    throw new RuntimeException(Errors.E07.getText());
                }
            }
        }
    }

    private void checkLineHaveStation(Color color) {
        for (Line line : lineList) {
            if (line.getColor() == color) {
                if (!line.getStationList().isEmpty()) {
                    throw new RuntimeException(Errors.E08.getText());
                }
            }
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(",", Metro.class.getSimpleName() + "[", "]")
                .add("city='" + city + "'")
                .add("lines='" + lineList + "'").toString();
    }
}
