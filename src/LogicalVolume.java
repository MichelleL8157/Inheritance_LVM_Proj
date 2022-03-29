import java.util.ArrayList;

public class LogicalVolume extends RunnerHelp {
    private int size;
    private VolumeGroup vG; //deleted from constructor

    public LogicalVolume(String lVName, int size, VolumeGroup vG) {
        super(lVName);
        this.size = size;
    }

    public String getLVName() { return super.getName(); }
    public VolumeGroup getvG() { return vG; }
    public int getSize() { return size; }
    public String getUUID() { return super.getUUIDuse(); }
}
