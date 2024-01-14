package infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Metro {
    private String city;
    private List<Line> lineList = new ArrayList<>();

    public Metro(String city) {
        this.city = city;
    }

    public void createNewLine(Color color, Metro metro) {
        if (checkLineColor(color)) {
            throw new RuntimeException("Ошибка: такая линия уже существует");
        }
        lineList.add(new Line(color, metro));
    }

    public void createFirstStationOnLine(Color colorLine, String nameStation, Metro metro) {
        if (!checkLineColor(colorLine)) {
            throw new RuntimeException("Ошибка: такой линии не найдено");
        }
        if (checkNameStation(nameStation)) {
            throw new RuntimeException("Ошибка: такая станция уже существует");
        }
        lineList.get(0).addStation(nameStation, metro, lineList.get(0));
        checkNameStation(nameStation);
    }

    private boolean checkLineColor(Color color) {
        for (Line line : lineList) {
            if (line.getColor() == color) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNameStation(String name) {
        for (int i = 0; i < lineList.size(); i++)
            for (int j = 0; j < lineList.get(i).getStationList().size(); j++) {
                if (Objects.equals(name, lineList.get(i).getStationList().get(j).getName())) {
                    return true;
                }
            }
        return false;
    }


}
