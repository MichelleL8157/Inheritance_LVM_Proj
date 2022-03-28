import java.util.ArrayList;

public class VolumeGroup extends RunnerHelp {
    private ArrayList<PhysicalVolume> pVList;
    private ArrayList<LogicalVolume> lVList; //no longer in constructor so checl

    public VolumeGroup(String vGName, PhysicalVolume pV) { //list of PV
        super(vGName);
        pVList.add(pV);
    }

    public String getVgName () { return super.getName(); }
    public String getUUID() { return super.getUUIDuse(); }
    public ArrayList<PhysicalVolume> getpVList() { return pVList; }
    public ArrayList<LogicalVolume> getlVList() { return lVList; }
}
