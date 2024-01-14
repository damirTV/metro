import infrastructure.Color;
import infrastructure.Metro;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Metro metro = new Metro("Пермь");
        metro.createNewLine(Color.RED, metro);
        metro.createNewLine(Color.BLUE, metro);
        metro.createFirstStationOnLine(Color.RED, "Спортивная", metro);
    }
}