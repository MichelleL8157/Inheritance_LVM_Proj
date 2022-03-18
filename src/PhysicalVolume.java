import java.util.ArrayList;

public class PhysicalVolume extends RunnerHelp {
    private ArrayList<PhysicalHardDrive> pVList;

    public PhysicalVolume(String pVName, PhysicalHardDrive pHD) {
        super(pVName);
        pVList.add(pHD);
    } //also has name, UUID

    public String getpVName() { return super.getName(); }
    public ArrayList<PhysicalHardDrive> getpVListVList() { return pVList; }
    public String getUUID() { return super.getUUIDuse(); }
}