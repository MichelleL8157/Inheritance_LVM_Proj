import java.io.Serializable;

public class LogicalVolume extends RunnerHelp implements Serializable {
    private int size;
    private VolumeGroup vG;

    public LogicalVolume(String lVName, int size, VolumeGroup vG) {
        super(lVName);
        this.size = size;
        this.vG = vG;
    }

    public String getLVName() { return super.getName(); }
    public VolumeGroup getVG() { return vG; }
    public int getSize() { return size; }
    public String getUUID() { return super.getUUIDuse(); }
}
