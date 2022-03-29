import java.util.ArrayList;

public class Commands {
    private String command;
    private ArrayList<PhysicalHardDrive> pHDList;
    private ArrayList<PhysicalHardDrive> pHDListUsed;
    private ArrayList<PhysicalVolume> pVList;
    private ArrayList<VolumeGroup> vGList;
    private ArrayList<LogicalVolume> lVList;

    public Commands() {
        pHDList = new ArrayList<PhysicalHardDrive>();
        pHDListUsed = new ArrayList<PhysicalHardDrive>();
        pVList = new ArrayList<PhysicalVolume>();
        vGList = new ArrayList<VolumeGroup>();
        lVList = new ArrayList<LogicalVolume>();
    }

    public void userCommand(String command) {
        if (command.indexOf("install-drive") == 0) {
            String leftOver = command.substring(14);
            String driveName = leftOver.substring(0, leftOver.indexOf(" "));
            String size = leftOver.substring(driveName.length() + 1, leftOver.length() - 1);
            PhysicalHardDrive pHD = new PhysicalHardDrive(driveName, Integer.parseInt(size));
            boolean goOn = true;
            for (PhysicalHardDrive pHDTest: pHDList) {
                if (pHDTest.getName().equals(driveName)) { goOn = false; }
            }
            if (goOn) {
                pHDList.add(pHD);
                System.out.println("Drive " + driveName + " installed");
            } else {
                System.out.println("error: " + driveName + " is already installed");
            }

        } else if (command.equals("list-drives")) {
            for (PhysicalHardDrive pHD: pHDList) {
                System.out.println(pHD.getName() + " [" + pHD.getSize() + "G]");
            }
        } else if (command.indexOf("pvcreate") == 0) {
            String leftOver = command.substring(9);
            String volName = leftOver.substring(0, leftOver.indexOf(" "));
            String driveName = leftOver.substring(volName.length() + 1);
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
                    boolean nameTaken = false;
                    for (PhysicalVolume pVTest: pVList) {
                        if (pVTest.getName().equals(volName)) {
                            nameTaken = true;
                            System.out.println("error: the name " + volName + " is already used");
                        }
                    }
                    if (!nameTaken) {
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
            }
        } else if (command.equals("pvlist")) {
            for (int i = 0; i != pVList.size(); i++) {
                PhysicalVolume pV = pVList.get(i);
                PhysicalHardDrive pHD = pHDListUsed.get(i);
                System.out.print(pV.getName() + ": [" + pHD.getSize() + "G] [");
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
            String pVName = leftOver.substring(volGName.length() + 1);
            PhysicalVolume pV = null;
            for (PhysicalVolume pVTest: pVList) {
                if (pVTest.getName().equals(pVName)) {
                    pV = pVTest;
                    break;
                }
            }
            if (pV == null) {
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
                    VolumeGroup vG = new VolumeGroup(volGName);
                    vG.addPVList(pV);
                    vGList.add(vG);
                    System.out.println(volGName + " created");
                }
            }
        } else if (command.indexOf("vgextend") == 0) {
            String leftOver = command.substring(9);
            String volGName = leftOver.substring(0, leftOver.indexOf(" "));
            String pVName = leftOver.substring(volGName.length() + 1);
            boolean exists = false;
            for (VolumeGroup vG: vGList) {
                if (vG.getName().equals(volGName)) {
                    exists = true;
                }
            }
            boolean pVExists = false;
            for (PhysicalVolume pV: pVList) {
                if (pV.getName().equals(pVName)) {
                    pVExists = true;
                }
            }
            if (!exists) {
                System.out.println("error: " + volGName + " does not exist");
            } else if (!pVExists) {
                System.out.println("error: " + pVName + "does not exist");
            } else {
                exists = false;
                for (VolumeGroup vG: vGList) {
                    PhysicalVolume pV = vG.getpVList().get(0);
                    if (pV.getName().equals(pVName)) {
                        System.out.println("error: Physical Volume name is already used");
                        exists = true;
                    }
                }
                if (!exists) {
                    for (VolumeGroup vG: vGList) {
                        if (vG.getName().equals(volGName)) {
                            vG.addPVList(new PhysicalVolume(pVName));
                            System.out.println("Successfully added " + pVName + " to " + volGName);
                            break;
                        }
                    }
                }
            }
        } else if (command.equals("vglist")) {
            for (int i = 0; i != vGList.size(); i++) {
                VolumeGroup vG = vGList.get(i);
                System.out.print(vG.getName() + ": total: [" + vG.getSize() + "] available: [" + vG.availableSpace() + "] [");
                for (int k = 0; k != vG.getpVList().size(); k++) {
                    PhysicalVolume pVTest = vG.getpVList().get(k);
                    System.out.print(pVTest.getName());
                    if (k != vG.getpVList().size() - 1) { System.out.print(", "); }
                }
                System.out.println("] [" + vG.getUUID() + "]");
            }
        } else if (command.indexOf("lvcreate") == 0) {
            String leftOver = command.substring(9);
            String lVName  = leftOver.substring(0, leftOver.indexOf(" "));
            leftOver = leftOver.substring(lVName.length() + 1);
            String size = leftOver.substring(0, leftOver.indexOf(" ") - 1);
            String vGName = leftOver.substring(size.length() + 2);
            System.out.println(lVName + "==" + size + "==" + vGName);
            VolumeGroup vG = null;
            LogicalVolume lV = new LogicalVolume(lVName, Integer.parseInt(size), vG);
            for (VolumeGroup vGTest: vGList) {
                if (vGTest.getName().equals(vGName)) {
                    vG = vGTest;
                    break;
                }
            }
            if (vG == null) {
                System.out.println("error: Volume Group does not exist");
            } else {
                boolean goOn = true;
                for (LogicalVolume lVTest: lVList) {
                    if (lVTest.getName().equals(lVName)) { goOn = false; }
                }
                if (goOn) {
                    if (vG.availableSpace() - Integer.parseInt(size) < 0) {
                        System.out.println("error: there is not enough space in the Volume Group");
                    } else {
                        vG.addLVList(lV);
                        lVList.add(lV);
                        System.out.println(vGName + " successfully installed");
                    }
                } else {
                    System.out.println("error: Logical Volume name exists");
                }
            }
        } else if (command.equals("lvlist")) {
            for (int i = 0; i != lVList.size(); i++) {
                LogicalVolume lV = lVList.get(i);
                System.out.println(lV.getName() + ": [" + lV.getSize() + "] [" + lV.getvG() + "] [" + lV.getUUID() + "]");
            }
        } else {
            System.out.println("That is not in the list of your commands.");
        }
    }

    public void installDrive() {

    }
}
