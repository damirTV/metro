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
        metro.createFirstStation(Color.RED, "Спортивная");
        metro.createLastStation(Color.RED, "Медведковская", Duration.ofSeconds(141));
        metro.createLastStation(Color.RED, "Молодежная", Duration.ofSeconds(118));
        metro.createLastStation(Color.RED, "Пермь 1", Duration.ofSeconds(180));
        metro.createLastStation(Color.RED, "Пермь 2", Duration.ofSeconds(130));
        metro.createLastStation(Color.RED, "Дворец Культуры", Duration.ofSeconds(266));

        List<String> changeStations = new ArrayList<>();
        changeStations.add("Пермь 1");

        metro.createNewLine(Color.BLUE, metro);
        metro.createFirstStation(Color.BLUE, "Пацанская");
        metro.createLastStation(Color.BLUE, "Улица Кирова", Duration.ofSeconds(90));
        metro.createLastStation(Color.BLUE, "Тяжмаш", Duration.ofSeconds(107),
                changeStations);
        metro.createLastStation(Color.BLUE, "Нижнекамская", Duration.ofSeconds(199));
        metro.createLastStation(Color.BLUE, "Соборная", Duration.ofSeconds(108));
        System.out.println(metro); // Печать всех линий и станций в метро

        metro.saleTicket(LocalDate.of(2024, 1, 24),
                metro.getStationByName("Пермь 2"),
                metro.getStationByName("Спортивная"));
        metro.saleTicket(LocalDate.of(2024, 1, 24),
                metro.getStationByName("Пермь 2"),
                metro.getStationByName("Пацанская"));

        metro.salePassMonth(LocalDate.now(), "Пермь 2");

        metro.salePassMonth(LocalDate.now(), "Пермь 1");
        metro.salePassMonth(LocalDate.now(), "Пермь 1");
        metro.renewPassMonth("Дворец Культуры", "а0001", LocalDate.of(2024, 2, 24));
        metro.getRevenueByDate(); // Печать дохода касс со всех станций в метро по дням, в которые были продажи
    }
}