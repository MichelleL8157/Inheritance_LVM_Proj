import java.io.Serializable;
import java.util.ArrayList;

public class VolumeGroup extends RunnerHelp implements Serializable {
    private ArrayList<PhysicalVolume> pVList;
    private ArrayList<LogicalVolume> lVList;
    private int size;

    public VolumeGroup(String vGName) {
        super(vGName);
        pVList = new ArrayList<PhysicalVolume>();
        lVList = new ArrayList<LogicalVolume>();
        size = 0;
    }

    public int availableSpace() {
        int spend = 0;
        for (LogicalVolume lV: lVList) {
            spend += lV.getSize();
        }
        return size - spend;
    }

    public void addLVList(LogicalVolume lV) {
        lVList.add(lV);
    }
    public void addPVList(PhysicalVolume pV) {
        pVList.add(pV);
        size += pV.getPHD().getSize();
    }

    public int getSize() { return size; }
    public String getVgName () { return super.getName(); }
    public String getUUID() { return super.getUUIDuse(); }
    public ArrayList<PhysicalVolume> getpVList() { return pVList; }
    public ArrayList<LogicalVolume> getlVList() { return lVList; }
}
