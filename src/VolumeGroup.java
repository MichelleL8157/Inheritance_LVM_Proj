public class VolumeGroup {
    private String UUID;
    private String name;
    private int size;

    public VolumeGroup(String name, PhysicalVolume PV) {
        this.name = name;
        pvIsUsed(PV);
    }

    public String getUUID() { return UUID; }
    public String

    public boolean pvIsUsed(PhysicalVolume PV) {
        if (physicalVolumesList.contains(PV)) {
            System.out.println("Error: ");
        } else {

        }
    }
}
