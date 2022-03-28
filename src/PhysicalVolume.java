import java.util.ArrayList;

public class PhysicalVolume extends RunnerHelp {
    private ArrayList<PhysicalHardDrive> pHDList;

    public PhysicalVolume(String pVName) {
        super(pVName);
    } //also has name, UUID

    public void attachPHDList(PhysicalHardDrive pHD) {
        pHDList.add(pHD);
    }

    public String getpVName() { return super.getName(); }
    public ArrayList<PhysicalHardDrive> getPHDList() { return pHDList; }
    public String getUUID() { return super.getUUIDuse(); }
}