package errors;

public enum Errors {
    STATION_BY_NAME_NOT_FOUND("Станция не найдена по имени"),
    CHANGE_TIME_0_SEC("Время перегона не может быть 0 сек."),
    PREV_STATION_HAVE_NEXT_STATION("Предыдущая станция имеет следующую станцию"),
    LINE_NOT_HAVE_STATION("У линии отсутствуют станции"),
    LINE_NOT_FOUND("Линия не найдена"),
    DIFFERENT_COLOR_LINES("Цвет линий у станций не совпадает"),
    NAME_STATION_ALREADY_EXISTS("Такая станция уже существует"),
    LINE_HAVE_STATION("У этой линии уже есть станции"),
    COLOR_LINE_ALREADY_EXISTS("Линия уже существует"),
    NOT_FOUND_CHANGE_STATION("Не найдено станции для пересадки"),
    PASS_MONTH_NOT_EXISTS("Месячного абонемента с таким номером не найдено"),
    NOT_CORRECT_NUMBER("Некорректный номер месячного абонемента");

    private final String text;

    Errors(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
