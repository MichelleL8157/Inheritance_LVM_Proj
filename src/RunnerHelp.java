import java.util.ArrayList;
import java.util.UUID;

public class RunnerHelp { //RH has PHD
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
        System.out.println(u.toString());
    }
}
