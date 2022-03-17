import java.util.UUID;

public class PhysicalVolume extends PhysicalHardDrive {
    private String UUID;
    private String pVName;
    

    public PhysicalVolume(String pVName, String pHDName) {
        UUID = RunnerHelp.makeUUID();

    }

    public static String makeUUID() {
        java.util.UUID u = java.util.UUID.randomUUID();
        System.out.println(u.toString());
    }
}
