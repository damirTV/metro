package infrastructure;

public enum Color {
    RED("Красная"),
    BLUE("Синяя");

    private final String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
