import java.util.ArrayList;

public class PhysicalVolume extends RunnerHelp {
    private ArrayList<PhysicalHardDrive> pVList;

    public PhysicalVolume(String pVName) {
        super(pVName);
    } //also has name, UUID

    public void attachPVList(PhysicalHardDrive pHD) {
        pVList.add(pHD);
    }

    public String getpVName() { return super.getName(); }
    public ArrayList<PhysicalHardDrive> getpVListVList() { return pVList; }
    public String getUUID() { return super.getUUIDuse(); }
}