import java.io.Serializable;

public class PhysicalVolume extends RunnerHelp implements Serializable {
    private PhysicalHardDrive pHD;

    public PhysicalVolume(String pVName) {
        super(pVName);
        pHD = null;
    }

    public void setPHD(PhysicalHardDrive pHD) { this.pHD = pHD; }

    public PhysicalHardDrive getPHD() { return pHD; }
    public String getUUID() { return super.getUUIDuse(); }
}