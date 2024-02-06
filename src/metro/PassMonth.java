package metro;

import java.time.LocalDate;

public class PassMonth {
    private static String serias;
    private static int counter = -1;
    private String number;
    private LocalDate saleDate;
    private LocalDate expireDate;

    public PassMonth(String serias, LocalDate saleDate, LocalDate expireDate) {
        PassMonth.serias = serias;
        this.saleDate = saleDate;
        this.expireDate = expireDate;
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
}
