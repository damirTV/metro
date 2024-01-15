package infrastructure;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Metro {
    private final String city;
    private List<Line> lineList = new ArrayList<>();

    public Metro(String city) {
        this.city = city;
    }

    public void createNewLine(Color color, Metro metro) {
        for (Line line : lineList) {
            if (line.getColor() == color) {
                throw new RuntimeException("Ошибка при создании новой линии:"
                        + " такая линия уже существует");
            }
        }
        lineList.add(new Line(color, metro));
    }

    public void createFirstStationLine(Color color, String nameStation, Metro metro) {
        checkLineColor(color);
        checkNameStation(nameStation);
        checkLineHaveStation(color);
        findLineByColor(color).addStation(nameStation, metro, findLineByColor(color));
    }

    public void createFirstStationLine(Color color, String nameStation, Metro metro,
                                       List<String> changeStations) {
        checkLineColor(color);
        checkNameStation(nameStation);
        checkLineHaveStation(color);
        findLineByColor(color).addStation(nameStation, metro, findLineByColor(color));
        Station lastStation = getLastStation(color);

        for (String station : changeStations) { // TODO - убрать в отдельный метод
            Station changeStation = getStationByName(station);
            changeStation.setStationChangeList(lastStation);
            lastStation.setStationChangeList(changeStation);
        }

    }

    public void createLastStationLine(Color color, String nameStation, Metro metro,
                                      Duration duration) {
        checkLineColor(color);
        Station prevStation = getLastStation(color);
        checkStationHaveNextStation(prevStation);
        checkTimeToNextStation(duration);
        checkNameStation(nameStation);
        prevStation.setTimeToNextStation(duration);
        findLineByColor(color).addStation(nameStation, metro, findLineByColor(color));
        prevStation.setNextStation(getLastStation(color));
        getLastStation(color).setPrevStation(prevStation);
    }

    public void createLastStationLine(Color color, String nameStation, Metro metro,
                                      Duration duration, List<String> changeStations) {
        checkLineColor(color);
        Station prevStation = getLastStation(color);
        checkStationHaveNextStation(prevStation);
        checkTimeToNextStation(duration);
        checkNameStation(nameStation);
        prevStation.setTimeToNextStation(duration);
        findLineByColor(color).addStation(nameStation, metro, findLineByColor(color));
        Station lastStation = getLastStation(color);
        prevStation.setNextStation(lastStation);
        lastStation.setPrevStation(prevStation);

        for (String station : changeStations) { // TODO - убрать в отдельный метод
            Station changeStation = getStationByName(station);
            changeStation.setStationChangeList(lastStation);
            lastStation.setStationChangeList(changeStation);
        }
    }

    private Station getStationByName(String name) {
        for (int i = 0; i < lineList.size(); i++) {
            for (int j = 0; j < lineList.get(i).getStationList().size(); j++) {
                if (Objects.equals(name, lineList.get(i).getStationList().get(j).getName())) {
                    return lineList.get(i).getStationList().get(j);
                }
            }
        }
        throw new RuntimeException("Ошибка: станции с таким именем не найдено");
    }

    private void checkTimeToNextStation(Duration duration) {
        if (duration == Duration.ZERO) {
            throw new RuntimeException("Ошибка: время перегона не может быть 0 сек.");
        }
    }

    private void checkStationHaveNextStation(Station station) {
        if (station.getNextStation() != null) {
            throw new RuntimeException("Ошибка: предыдущая станция имеет следующую станцию");
        }
    }

    private Station getLastStation(Color color) {
        if ((findLineByColor(color).getStationList().get(findLineByColor((color))
                .getStationList().size() - 1) == null)) {
            throw new RuntimeException("Ошибка при проверке пред. станции:"
                    + " у этой линии отсутствуют станции");
        }
        return findLineByColor(color).getStationList().get(findLineByColor((color))
                .getStationList().size() - 1);
    }

    private Line findLineByColor(Color color) {
        for (Line line : lineList) {
            if (line.getColor() == color) {
                return line;
            }
        }
        throw new RuntimeException("Ошибка при поиске линии: такой линии не найдено");
    }

    private void checkLineColor(Color color) {
        for (Line line : lineList) {
            if (line.getColor() == color) {
                return;
            }
        }
        throw new RuntimeException("Ошибка при проверке наличия линии:"
                + " такой линии не найдено");
    }

    private void checkNameStation(String name) {
        for (int i = 0; i < lineList.size(); i++) {
            for (int j = 0; j < lineList.get(i).getStationList().size(); j++) {
                if (Objects.equals(name, lineList.get(i).getStationList().get(j).getName())) {
                    throw new RuntimeException("Ошибка при проверке имени станции:"
                            + " такая станция уже существует");
                }
            }
        }
    }

    private void checkLineHaveStation(Color color) {
        for (int i = 0; i < lineList.size(); i++) {
            if (lineList.get(i).getColor() == color) {
                if (!lineList.get(i).getStationList().isEmpty()) {
                    throw new RuntimeException("Ошибка при проверке наличия станций у линии:"
                            + " у этой линии уже есть станции");
                }
            }
        }
    }
}
