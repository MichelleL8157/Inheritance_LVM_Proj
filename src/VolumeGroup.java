import java.util.ArrayList;

public class VolumeGroup extends RunnerHelp {
    private ArrayList<PhysicalVolume> pVList;
    private ArrayList<LogicalVolume> lVList;
    private int size;

    public VolumeGroup(String vGName) { //list of PV
        super(vGName);
        pVList = new ArrayList<PhysicalVolume>();
        lVList = new ArrayList<LogicalVolume>();
    }

    public int getSize() {
        int total = 0;
        for (PhysicalVolume pV: pVList) {
            ArrayList<PhysicalHardDrive> pHDList = pV.getPHDList();
            for (PhysicalHardDrive pHD: pHDList) {
                total += pHD.getSize();
                System.out.println("Doing ! " + total);
            }
        }
        return total;
    }

    public int availableSpace() {
        int spend = 0;
        for (LogicalVolume lV: lVList) {
            spend += lV.getSize();
        }
        return getSize() - spend;
    }

    public void addLVList(LogicalVolume lV) {
        lVList.add(lV);
    }

    public void addPVList(PhysicalVolume pV) {
        pVList.add(pV);
    }
    public String getVgName () { return super.getName(); }
    public String getUUID() { return super.getUUIDuse(); }
    public ArrayList<PhysicalVolume> getpVList() { return pVList; }
    public ArrayList<LogicalVolume> getlVList() { return lVList; }
}
