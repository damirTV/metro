import infrastructure.Color;
import infrastructure.Metro;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Metro metro = new Metro("Пермь");

        metro.createNewLine(Color.RED, metro);
        metro.createFirstStationLine(Color.RED, "Спортивная", metro);
        metro.createLastStationLine(Color.RED, "Медведковская", metro, Duration.ofSeconds(141));
        metro.createLastStationLine(Color.RED, "Молодежная", metro, Duration.ofSeconds(118));
        metro.createLastStationLine(Color.RED, "Пермь 1", metro, Duration.ofSeconds(180));
        metro.createLastStationLine(Color.RED, "Пермь 2", metro, Duration.ofSeconds(130));
        metro.createLastStationLine(Color.RED, "Дворец Культуры", metro, Duration.ofSeconds(266));

        List<String> changeStations = new ArrayList<>();
        changeStations.add("Пермь 1");

        metro.createNewLine(Color.BLUE, metro);
        metro.createFirstStationLine(Color.BLUE, "Пацанская", metro);
        metro.createLastStationLine(Color.BLUE, "Улица Кирова", metro, Duration.ofSeconds(90));
        metro.createLastStationLine(Color.BLUE, "Тяжмаш", metro, Duration.ofSeconds(107),
                changeStations);
        metro.createLastStationLine(Color.BLUE, "Нижнекамская", metro, Duration.ofSeconds(199));
        metro.createLastStationLine(Color.BLUE, "Соборная", metro, Duration.ofSeconds(108));
    }
}