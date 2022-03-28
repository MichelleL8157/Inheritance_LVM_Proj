import java.util.ArrayList;

public class Commands {
    private String command;
    private ArrayList<PhysicalHardDrive> pHDList;
    private ArrayList<PhysicalHardDrive> pHDListUsed;
    private ArrayList<PhysicalVolume> pVList;
    private ArrayList<VolumeGroup> vGList;
    private ArrayList<LogicalVolume> lVList;

    public Commands(String command) {
        this.command = command;
        pHDList = new ArrayList<PhysicalHardDrive>();
        pHDListUsed = new ArrayList<PhysicalHardDrive>();
        pVList = new ArrayList<PhysicalVolume>();
        vGList = new ArrayList<VolumeGroup>();
        lVList = new ArrayList<LogicalVolume>();
    }

    public void userCommand() {
        if (command.indexOf("install-drive") == 0) {
            String leftOver = command.substring(14);
            String driveName = leftOver.substring(0, leftOver.indexOf(" "));
            String size = leftOver.substring(driveName.length(), leftOver.length() - 1);
            PhysicalHardDrive pHD = new PhysicalHardDrive(driveName, Integer.parseInt(size));
            pHDList.add(pHD);
            System.out.println("Drive " + driveName + " installed");
        } else if (command.equals("list-drives")) {
            for (PhysicalHardDrive pHD: pHDList) {
                System.out.print(pHD.getName() + " [" + pHD.getSize() + "G]");
            }
        } else if (command.indexOf("pvcreate") == 0) {
            String leftOver = command.substring(9);
            String volName = leftOver.substring(0, leftOver.indexOf(" "));
            String driveName = leftOver.substring(volName.length(), leftOver.length() - 1);
            PhysicalVolume pV = new PhysicalVolume(volName);
            boolean exists = false;
            for (PhysicalHardDrive pHD : pHDList) {
                if (pHD.getName().equals(driveName)) {
                    exists = true;
                }
            }
            if (!exists) {
                System.out.println("error: the drive does not exist");
            } else {
                boolean goOn = true;
                for (PhysicalHardDrive pHD : pHDListUsed) {
                    if (pHD.getName().equals(driveName)) {
                        System.out.println("error: the drive is associated with another Physical Volume");
                        goOn = false;
                        break;
                    }
                }
                if (goOn) {
                    for (PhysicalHardDrive pHDTest : pHDList) {
                        if (pHDTest.getName().equals(driveName)) {
                            PhysicalHardDrive pHD = pHDTest;
                            pVList.add(pV);
                            pHDListUsed.add(pHD);
                            System.out.println(volName + " created");
                            break;
                        }
                    }
                }

            }
        } else if (command.equals("pvlist")) {
            for (int i = 0; i != pVList.size(); i++) {
                PhysicalVolume pV = pVList.get(i);
                PhysicalHardDrive pHD = pHDListUsed.get(i);
                System.out.print(pV.getName() + ": [" + pHD.getSize() + "] [");
                for (VolumeGroup vG: vGList) {
                    if (vG.getpVList().contains(pV)) {
                        System.out.print(vG.getName());
                        break;
                    }
                }
                System.out.println("] [" + pV.getUUID() + "]");
            }
        } else if (command.indexOf("vgcreate") == 0) {
            String leftOver = command.substring(9);
            String volGName = leftOver.substring(0, leftOver.indexOf(" "));
            String pVName = leftOver.substring(volGName.length(), leftOver.length() - 1);
            PhysicalVolume pV = new PhysicalVolume(null);
            boolean exists = false;
            for (PhysicalVolume pVTest: pVList) {
                if (pVTest.getName().equals(pVName)) {
                    pV = pVTest;
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                System.out.println("error: Physical Volume specified doesn't exist");
            } else {
                boolean goOn = true;
                for (VolumeGroup vGTest: vGList) {
                    ArrayList<PhysicalVolume> pVListTest = vGTest.getpVList();
                    for (PhysicalVolume pVTest: pVListTest) {
                        if (pVTest.getName().equals(pVName)) {
                            System.out.println("error: Physical Volume specific is part of another VG");
                            goOn = false;
                        }
                    }
                }
                if (goOn) {
                    for (VolumeGroup vGTest: vGList) {
                        if (vGTest.getName().equals(volGName)) {
                            goOn = false;
                            System.out.println("error: Volume Group name already exists");
                            break;
                        }
                    }
                }
                if (goOn) {
                    VolumeGroup vG = new VolumeGroup(volGName, pV);
                }
            }

        } else if (command.equals("vglist")) {

        } else if (command.indexOf("lvcreate") == 0) {

        } else if (command.equals("lvlist")) {

        } else {
            System.out.println("That is not in the list of your commands.");
        }
    }

    public void installDrive() {

    }
}
