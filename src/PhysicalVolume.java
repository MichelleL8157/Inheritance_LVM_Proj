public class PhysicalVolume {
    private static String UUID;
    private String name;
    private PhysicalHardDrive PV;

    public PhysicalVolume(String name, PhysicalHardDrive PV) {
        UUID = RunnerHelp.makeUUID();
        this.name = name;
        this.PV = PV;
    }

}
