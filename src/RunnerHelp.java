import java.util.UUID;

public class RunnerHelp {
    private String name;
    private String UUIDuse;

    public RunnerHelp(String name) {
        this.name = name;
        UUIDuse = makeUUID();
    }

    public String getName() { return name; }
    public String getUUIDuse() { return UUIDuse; }

    public static String makeUUID() {
        UUID u = UUID.randomUUID();
        return u.toString();
    }
}
