import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import errors.MetroException;
import line.Color;
import metro.Metro;

public class Main {
    public static void main(String[] args) throws MetroException {
        Metro metro = new Metro("Пермь");

        metro.createNewLine(Color.RED, metro);
        metro.createFirstStation(Color.RED, "Спортивная", metro);
        metro.createLastStation(Color.RED, "Медведковская", metro, Duration.ofSeconds(141));
        metro.createLastStation(Color.RED, "Молодежная", metro, Duration.ofSeconds(118));
        metro.createLastStation(Color.RED, "Пермь 1", metro, Duration.ofSeconds(180));
        metro.createLastStation(Color.RED, "Пермь 2", metro, Duration.ofSeconds(130));
        metro.createLastStation(Color.RED, "Дворец Культуры", metro, Duration.ofSeconds(266));

        List<String> changeStations = new ArrayList<>();
        changeStations.add("Пермь 1");

        metro.createNewLine(Color.BLUE, metro);
        metro.createFirstStation(Color.BLUE, "Пацанская", metro);
        metro.createLastStation(Color.BLUE, "Улица Кирова", metro, Duration.ofSeconds(90));
        metro.createLastStation(Color.BLUE, "Тяжмаш", metro, Duration.ofSeconds(107),
                changeStations);
        metro.createLastStation(Color.BLUE, "Нижнекамская", metro, Duration.ofSeconds(199));
        metro.createLastStation(Color.BLUE, "Соборная", metro, Duration.ofSeconds(108));
        System.out.println(metro);
        System.out.println(metro.getNumberStagesBetweenStationsDifferentLines(metro.getStationByName("Пермь 2"),
                metro.getStationByName("Пацанская")));

        metro.saleTicket(LocalDate.of(2024, 1, 24),
                metro.getStationByName("Пермь 2"),
                metro.getStationByName("Спортивная"));
        metro.saleTicket(LocalDate.of(2024, 1, 24),
                metro.getStationByName("Пермь 2"),
                metro.getStationByName("Пацанская"));
        metro.getRevenueByStation(metro.getStationByName("Пермь 2"));
        metro.salePassMonth(LocalDate.of(2024, 1, 24),
                metro.getStationByName("Пермь 2"));
        metro.getRevenueByStation(metro.getStationByName("Пермь 2"));
    }
}