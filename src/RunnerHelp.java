import java.util.ArrayList;
import java.util.UUID;

public class RunnerHelp { //Super class?
    private static ArrayList<PhysicalVolume> PVList;
    private static ArrayList<LogicalVolume> LVList;
    private static ArrayList<PhysicalHardDrive> PHDList;

    public RunnerHelp() {
        PVList = new ArrayList<PhysicalVolume>();
        LVList = new ArrayList<LogicalVolume>();
        PHDList = new ArrayList<PhysicalHardDrive>();
    }



    public static String makeUUID() {
        UUID u = UUID.randomUUID();
        System.out.println(u.toString());
    }
}

