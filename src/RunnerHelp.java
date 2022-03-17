import java.util.ArrayList;
import java.util.UUID;

public class RunnerHelp extends PhysicalHardDrive {
    private ArrayList<PhysicalHardDrive> pHDList;
    private ArrayList<String> checkPHDNames;

    public RunnerHelp() {
        pHDList = new ArrayList<PhysicalHardDrive>();
        checkPHDNames = new ArrayList<String>();
    }

    public ArrayList<PhysicalHardDrive> getPHDList() {
        return pHDList;
    }

    public void addPHDList(PhysicalHardDrive pV) {
        pHDList.add(pV);
    }

    public static String makeUUID() {
        UUID u = UUID.randomUUID();
        System.out.println(u.toString());
    }
}

