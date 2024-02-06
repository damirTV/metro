package metro;

import errors.Errors;
import errors.MetroException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import line.Color;
import line.Line;
import station.Station;

public class Metro {
    private final String city;
    private Set<Line> lines = new HashSet<>();
    private List<PassMonth> passMonths = new LinkedList<>();

    public Metro(String city) {
        this.city = city;
    }

    public Station getStationByName(String name) { // модификатор public для тестирования
        return lines.stream()
                .flatMap(line -> line.getStations().stream())
                .filter(station -> station.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(Errors.STATION_BY_NAME_NOT_FOUND.getText()));
    }

    private Station getLastStation(Color color) {
        if (findLineByColor(color).getLastStation() == null) {
            throw new RuntimeException(Errors.LINE_NOT_HAVE_STATION.getText());
        }
        return findLineByColor(color).getLastStation();
    }

    public int getNumberStagesBetweenStationsDifferentLines(Station firstStation, Station lastStation)
            throws MetroException {
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

    public int getNumberStagesBetweenStationsSameLines(Station firstStation, Station lastStation)
            throws MetroException {
        if (getNumberStagesBetweenNextStations(firstStation, lastStation) != -1) {
            return getNumberStagesBetweenNextStations(firstStation, lastStation);
        }
        if (getNumberStagesBetweenPrevStations(firstStation, lastStation) != -1) {
            return getNumberStagesBetweenPrevStations(firstStation, lastStation);
        }
        throw new MetroException("Нет пути из станции "
                + firstStation.getName() + " в " + lastStation.getName());
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

    public void getRevenueByDate() {
        List<Station> stations = lines.stream() // Формируем список станций
                .flatMap(value -> value.getStations().stream()).toList();

        TreeMap<LocalDate, Long> revenueByDate = stations.stream()
                .flatMap(value -> value
                        .getRevenue()
                        .entrySet()
                        .stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        TreeMap::new,
                        Collectors.summingLong(Map.Entry::getValue)
        ));
        System.out.println(revenueByDate);
    }

    private void setStationChangeList(Station lastStation, List<String> changeStationNames) {
        changeStationNames.stream().map(this::getStationByName)
               .forEach(station -> {
                   station.setStationChangeList(lastStation);
                   lastStation.setStationChangeList(station);
               });
    }

    public Station findChangeStation(Line lineFrom, Line lineTo) {
        return lineFrom.getChangeStations().stream()
                .filter(stationLineFrom -> stationLineFrom
                        .getStationChangeList()
                        .contains(findStationByLine(stationLineFrom
                                .getStationChangeList(), lineTo)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(Errors.NOT_FOUND_CHANGE_STATION.getText()));
    }

    private Station findStationByLine(Set<Station> stationChangeSet, Line line) {
        return stationChangeSet.stream()
                .filter(value -> value.getLine() == line)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(Errors.NOT_FOUND_CHANGE_STATION.getText()));
    }

    private Line findLineByColor(Color color) {
        return lines.stream()
                .filter(value -> value.getColor() == color)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(Errors.LINE_NOT_FOUND.getText()));
    }

    public void saleTicket(LocalDate date, Station firstStation, Station lastStation) throws MetroException {
        firstStation.saleTicket(date,
                getNumberStagesBetweenStationsDifferentLines(firstStation, lastStation));
    }

    public void salePassMonth(LocalDate date, String stationName) {
        String serias = "а"; // Серия проездного билета
        PassMonth passMonth = new PassMonth(serias, date, date.plusMonths(1));
        passMonth.setNumber(serias + counterToNumber(passMonth.getCounter()));
        passMonths.add(passMonth);
        getStationByName(stationName).salePassMonth(date);
    }

    public String counterToNumber(int passCounter) {
        if (passCounter < 10) {
            return "000" + passCounter;
        }
        if (passCounter < 100) {
            return "00" + passCounter;
        }
        if (passCounter < 1000) {
            return "0" + passCounter;
        }
        return Integer.toString(passCounter);
    }

    public void renewPassMonth(String nameStation, String number, LocalDate date) {
        getStationByName(nameStation).renewPassMonth(passMonths, number, date);
    }

    public void createNewLine(Color color, Metro metro) {
        checkLineHaveColor(color);
        lines.add(new Line(color, metro));
    }

    public void createFirstStation(Color color, String nameStation) {
        checkForFirstStation(color, nameStation);
        findLineByColor(color).addStation(nameStation);
    }

    public void createFirstStation(Color color, String nameStation,
                                   List<String> changeStations) {
        checkForFirstStation(color, nameStation);
        findLineByColor(color).addStation(nameStation);
        setStationChangeList(getLastStation(color), changeStations);

    }

    public void createLastStation(Color color, String nameStation,
                                  Duration duration) {
        commonCreateLastStation(duration, color, nameStation,
                findLineByColor(color), getLastStation(color));
    }

    public void createLastStation(Color color, String nameStation,
                                  Duration duration, List<String> changeStations) {
        checkForLastStation(color, nameStation, getLastStation(color), duration);
        commonCreateLastStation(duration, color, nameStation,
                findLineByColor(color), getLastStation(color));
        setStationChangeList(getLastStation(color), changeStations);
    }

    private void commonCreateLastStation(Duration duration, Color color, String nameStation,
                                         Line line, Station prevStation) {
        checkForLastStation(color, nameStation, prevStation, duration);
        prevStation.setTimeToNextStation(duration);
        findLineByColor(color).addStation(nameStation);
        Station lastStation = getLastStation(color);
        prevStation.setNextStation(lastStation);
        lastStation.setPrevStation(prevStation);
    }

    public boolean checkPassMonthExpires(String number, LocalDate date) {
        Optional<PassMonth> passMonth = Optional.ofNullable(passMonths.stream()
                .filter(value -> number.equals(value.getNumber()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(Errors.PASS_MONTH_NOT_EXISTS.getText())));
        return passMonth.stream()
                .anyMatch(value -> date.isAfter(value.getExpireDate())); // false - билет действителен
    }

    private void checkForFirstStation(Color color, String nameStation) {
        checkLineColor(color);
        checkNameStation(nameStation);
        checkLineExists(color);
    }

    private void checkForLastStation(Color color, String nameStation,
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


    private void checkLineColor(Color color) { // Выходит ошибка, если линия не найдена
        lines.stream()
                .filter(value -> value.getColor() == color)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(Errors.LINE_NOT_FOUND.getText()));
    }

    private void checkLineHaveColor(Color color) { // Выходит ошибка, если линия найдена
        lines.stream()
                .filter(value -> value.getColor() == color)
                .findFirst()
                .ifPresent(value -> {
                    throw new RuntimeException(Errors.COLOR_LINE_ALREADY_EXISTS.getText());
                });
    }

    private void checkNameStation(String name) {
        lines.stream()
                .flatMap(line -> line.getStations().stream()
                        .filter(station -> station.getName().equals(name)))
                .findAny()
                .ifPresent((station -> {
                    throw new RuntimeException(Errors.NAME_STATION_ALREADY_EXISTS.getText());
                }));
    }

    private void checkLineExists(Color color) {
        findLineByColor(color).checkStationList();
    }

    @Override
    public String toString() {
        return "Metro{city='" + city + "', lines=" + lines + "}";
    }
}
