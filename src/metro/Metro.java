package metro;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import line.Color;
import line.Line;
import station.Station;

public class Metro {
    private final String city;
    private Set<Line> lines = new HashSet<>();

    public Metro(String city) {
        this.city = city;
    }

    public Station getStationByName(String name) { // TODO - переделать в private
        for (Line line : lines) {
            for (Station station : line.getStationList()) {
                if (Objects.equals(name, station.getName())) {
                    return station;
                }
            }
        }
        throw new RuntimeException(Errors.STATION_BY_NAME_NOT_FOUND.getText());
    }

    private Station getLastStation(Color color) {
        if (findLineByColor(color).getLastStation() == null) {
            throw new RuntimeException(Errors.LINE_NOT_HAVE_STATION.getText());
        }
        return findLineByColor(color).getLastStation();
    }

    public int getNumberStagesBetweenStationsDifferentLines(Station firstStation, Station lastStation) {

        if (firstStation.getLine() == lastStation.getLine()) {
            return getNumberStagesBetweenStationsSameLines(firstStation, lastStation);
        }
        Line firstLine = firstStation.getLine();
        Line lastLine = lastStation.getLine();
        int changesFirstLine = 0;
        if (findChangeStation(firstLine, lastLine) != firstStation) {
            Station changeStationFirstLine = findChangeStation(firstStation.getLine(), lastStation.getLine());
            changesFirstLine = getNumberStagesBetweenStationsSameLines(firstStation, changeStationFirstLine);
        }
        int changeLastLine = 0;
        if (findChangeStation(lastLine, firstLine) != lastStation) {
            Station changeStationLastLine = findChangeStation(lastStation.getLine(), firstStation.getLine());
            changeLastLine = getNumberStagesBetweenStationsSameLines(lastStation, changeStationLastLine);
        }
        return changesFirstLine + changeLastLine;
    }

    public int getNumberStagesBetweenStationsSameLines(Station firstStation, Station lastStation) {
        if (getNumberStagesBetweenNextStations(firstStation, lastStation) != -1) {
            return getNumberStagesBetweenNextStations(firstStation, lastStation);
        }
        if (getNumberStagesBetweenPrevStations(firstStation, lastStation) != -1) {
            return getNumberStagesBetweenPrevStations(firstStation, lastStation);
        }
        throw new RuntimeException(Errors.E11.getText() + " " + firstStation + " в " + lastStation); // TODO - создать свою ошибку
    }

    private int getNumberStagesBetweenNextStations(Station firstStation, Station lastStation) {
        if (firstStation.getLine() != lastStation.getLine()) {
            throw new RuntimeException(Errors.DIFFERENT_COLOR_LINES.getText());
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
            throw new RuntimeException(Errors.DIFFERENT_COLOR_LINES.getText());
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

    public void getRevenueByStation(Station saleStation) {
        System.out.println(saleStation.getRevenue());
   }

    private void setStationChangeList(Station lastStation, List<String> changeStations) {
        for (String station : changeStations) {
            Station changeStation = getStationByName(station);
            changeStation.setStationChangeList(lastStation);
            lastStation.setStationChangeList(changeStation);
        }
    }

    public Station findChangeStation(Line lineFrom, Line lineTo) {
        Set<Station> lineFromChangeStations = lineFrom.getChangeStations();
        for (Station stationLineFrom : lineFromChangeStations) {
            if (stationLineFrom.getStationChangeList().contains(findStationByLine(stationLineFrom
                    .getStationChangeList(), lineTo))) {
                return stationLineFrom;
            }
        }
        throw new RuntimeException(Errors.NOT_FOUND_CHANGE_STATION.getText());
    }

    private Station findStationByLine(Set<Station> stationChangeSet, Line line) {
        for (Station station : stationChangeSet) {
            if (station.getLine() == line) {
                return station;
            }
        }
        throw new RuntimeException(Errors.NOT_FOUND_CHANGE_STATION.getText());
    }

    public void saleTicket(LocalDate date, Station firstStation, Station lastStation) {
        firstStation.saleTicket(date,
                getNumberStagesBetweenStationsDifferentLines(firstStation, lastStation));
    }

    public void createNewLine(Color color, Metro metro) {
        checkLineHaveColor(color);
        lines.add(new Line(color, metro));
    }

    public void createFirstStation(Color color, String nameStation, Metro metro) {
        checksForFirstStation(color, nameStation);
        findLineByColor(color).addStation(nameStation);
    }

    public void createFirstStation(Color color, String nameStation, Metro metro,
                                   List<String> changeStations) {
        checksForFirstStation(color, nameStation);
        findLineByColor(color).addStation(nameStation);
        setStationChangeList(getLastStation(color), changeStations);

    }

    public void createLastStation(Color color, String nameStation, Metro metro,
                                  Duration duration) {
        Station prevStation = getLastStation(color);
        checksForLastStation(color, nameStation, prevStation, duration);
        commonCreateLastStation(duration, color, nameStation, metro,
                findLineByColor(color), prevStation);
    }

    public void createLastStation(Color color, String nameStation, Metro metro,
                                  Duration duration, List<String> changeStations) {
        Station prevStation = getLastStation(color);
        checksForLastStation(color, nameStation, prevStation, duration);
        commonCreateLastStation(duration, color, nameStation, metro,
                findLineByColor(color), prevStation);
        setStationChangeList(getLastStation(color), changeStations);
    }

    private void commonCreateLastStation(Duration duration, Color color, String nameStation,
                                         Metro metro, Line line, Station prevStation) {
        prevStation.setTimeToNextStation(duration);
        findLineByColor(color).addStation(nameStation);
        Station lastStation = getLastStation(color);
        prevStation.setNextStation(lastStation);
        lastStation.setPrevStation(prevStation);
    }

    private Line findLineByColor(Color color) {
        for (Line line : lines) {
            if (line.getColor() == color) {
                return line;
            }
        }
        throw new RuntimeException(Errors.LINE_NOT_FOUND.getText());
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
            throw new RuntimeException(Errors.CHANGE_TIME_0_SEC.getText());
        }
    }

    private void checkStationHaveNextStation(Station station) {
        if (station.getNextStation() != null) {
            throw new RuntimeException(Errors.PREV_STATION_HAVE_NEXT_STATION.getText());
        }
    }


    private void checkLineColor(Color color) {
        for (Line line : lines) {
            if (line.getColor() == color) {
                return;
            }
        }
        throw new RuntimeException(Errors.LINE_NOT_FOUND.getText());
    }

    private void checkLineHaveColor(Color color) {
        for (Line line : lines) {
            if (line.getColor() == color) {
                throw new RuntimeException(Errors.COLOR_LINE_ALREADY_EXISTS.getText());
            }
        }
    }


    private void checkNameStation(String name) {

        for (Line line : lines) {
            for (Station station : line.getStationList()) {
                if (Objects.equals(name, station.getName())) {
                    throw new RuntimeException(Errors.NAME_STATION_ALREADY_EXISTS.getText());
                }
            }
        }
    }

    private void checkLineHaveStation(Color color) {
        if (!findLineByColor(color).getStationList().isEmpty()) {
            throw new RuntimeException(Errors.LINE_HAVE_STATION.getText());
        }
    }

    @Override
    public String toString() {
        return "Metro{city='" + city + "', lines=" + lines + "}";
    }
}
