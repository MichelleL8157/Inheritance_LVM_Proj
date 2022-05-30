import java.io.*;
import java.util.ArrayList;

public class Commands{
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
        this.command = command;
        if (command.indexOf("install-drive") == 0) createDrive();
        else if (command.equals("list-drives")) displayDrives();
        else if (command.indexOf("pvcreate") == 0) createPV();
        else if (command.equals("pvlist")) displayPVList();
        else if (command.indexOf("vgcreate") == 0) createVG();
        else if (command.indexOf("vgextend") == 0) extendVG();
        else if (command.equals("vglist")) displayVGList();
        else if (command.indexOf("lvcreate") == 0) createLV();
        else if (command.equals("lvlist")) displayLVList();
        else System.out.println("error: That is not in the list of your commands.");
    }

    public void createDrive() {
        String leftOver = command.substring(14);
        String driveName = leftOver.substring(0, leftOver.indexOf(" "));
        String size = leftOver.substring(driveName.length() + 1, leftOver.length() - 1);
        PhysicalHardDrive pHD = new PhysicalHardDrive(driveName, Integer.parseInt(size));
        boolean goOn = true;
        for (PhysicalHardDrive pHDTest: pHDList) {
            if (pHDTest.getName().equals(driveName)) {
                goOn = false;
                break;
            }
        }
        if (goOn) {
            pHDList.add(pHD);
            System.out.println("Drive " + driveName + " installed");
        } else {
            System.out.println("error: " + driveName + " is already installed");
        }

    }

    public void displayDrives() {
        for (PhysicalHardDrive pHD: pHDList) {
            System.out.println(pHD.getName() + " [" + pHD.getSize() + "G]");
        }
    }

    public void createPV() {
        String leftOver = command.substring(9);
        String pvName = leftOver.substring(0, leftOver.indexOf(" "));
        String pHDName = leftOver.substring(pvName.length() + 1);
        PhysicalVolume pV = new PhysicalVolume(pvName);
        boolean exists = false;
        for (PhysicalHardDrive pHD: pHDList) {
            if (pHD.getName().equals(pHDName)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            System.out.println("error: the drive does not exist");
        } else {
            boolean pHDUsed = false;
            for (PhysicalHardDrive pHD: pHDListUsed) {
                if (pHD.getName().equals(pHDName)) {
                    System.out.println("error: the drive is associated with another Physical Volume");
                    pHDUsed = true;
                    break;
                }
            }
            if (!pHDUsed) {
                boolean pVNameTaken = false;
                for (PhysicalVolume pVTest: pVList) {
                    if (pVTest.getName().equals(pvName)) {
                        pVNameTaken = true;
                        System.out.println("error: the name " + pvName + " is already used");
                    }
                }
                if (!pVNameTaken) {
                    for (PhysicalHardDrive pHDTest: pHDList) {
                        if (pHDTest.getName().equals(pHDName)) {
                            PhysicalHardDrive pHD = pHDTest;
                            pV.setPHD(pHD);
                            pVList.add(pV);
                            pHDListUsed.add(pHD);
                            System.out.println(pvName + " created");
                            break;
                        }
                    }
                }
            }
        }
    }

    public void displayPVList() {
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
    }

    public void createVG() {
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
            boolean used = false;
            for (VolumeGroup vGTest: vGList) {
                ArrayList<PhysicalVolume> pVListTest = vGTest.getpVList();
                for (PhysicalVolume pVTest: pVListTest) {
                    if (pVTest.getName().equals(pVName)) {
                        System.out.println("error: Physical Volume specified is part of another VG");
                        used = true;
                    }
                }
            }
            if (!used) {
                for (VolumeGroup vGTest: vGList) {
                    if (vGTest.getName().equals(volGName)) {
                        System.out.println("error: Volume Group name already exists");
                        used = true;
                        break;
                    }
                }
                if (!used) {
                    VolumeGroup vG = new VolumeGroup(volGName);
                    vG.addPVList(pV);
                    vGList.add(vG);
                    System.out.println(volGName + " created");
                }
            }
        }
    }

    public void displayVGList() {
        for (int i = 0; i != vGList.size(); i++) {
            VolumeGroup vG = vGList.get(i);
            System.out.print(vG.getName() + ": total: [" + vG.getSize() + "G] available: [" + vG.availableSpace() + "G] [");
            for (int k = 0; k != vG.getpVList().size(); k++) {
                PhysicalVolume pV = vG.getpVList().get(k);
                System.out.print(pV.getName());
                if (k != vG.getpVList().size() - 1) System.out.print(", ");
            }
            System.out.println("] [" + vG.getUUID() + "]");
        }
    }

    public void extendVG() {
        String leftOver = command.substring(9);
        String vGName = leftOver.substring(0, leftOver.indexOf(" "));
        String pVName = leftOver.substring(vGName.length() + 1);
        boolean vGExists = false;
        for (VolumeGroup vGTest: vGList) {
            if (vGTest.getName().equals(vGName)) {
                vGExists = true;
            }
        }
        boolean pVExists = false;
        for (PhysicalVolume pVTest: pVList) {
            if (pVTest.getName().equals(pVName)) {
                pVExists = true;
            }
        }
        if (!vGExists || !pVExists) {
            if (!vGExists) System.out.println("error: " + vGName + " does not exist");
            if (!pVExists) System.out.println("error: " + pVName + "does not exist");
        } else {
            boolean pVUsed = false;
            for (VolumeGroup vGTest: vGList) {
                for (PhysicalVolume pVTest: vGTest.getpVList()) {
                    if (pVTest.getName().equals(pVName)) {
                        System.out.println("error: Physical Volume name is already used");
                        pVUsed = true;
                        break;
                    }
                }
            }
            if (!pVUsed) {
                PhysicalVolume pV = null;
                for (PhysicalVolume pVTest: pVList) {
                    if (pVTest.getName().equals(pVName)) {
                        pV = pVTest;
                        break;
                    }
                }
                for (VolumeGroup vG: vGList) {
                    if (vG.getName().equals(vGName)) {
                        vG.addPVList(pV);
                        System.out.println("Successfully added " + pVName + " to " + vGName);
                        break;
                    }
                }
            }
        }
    }

    public void createLV() {
        String leftOver = command.substring(9);
        String lVName  = leftOver.substring(0, leftOver.indexOf(" "));
        leftOver = leftOver.substring(lVName.length() + 1);
        String size = leftOver.substring(0, leftOver.indexOf(" ") - 1);
        String vGName = leftOver.substring(size.length() + 2);
        VolumeGroup vG = null;
        for (VolumeGroup vGTest: vGList) {
            if (vGTest.getName().equals(vGName)) {
                vG = vGTest;
                break;
            }
        }
        if (vG == null) {
            System.out.println("error: Volume Group does not exist");
        } else {
            boolean lVExists = false;
            LogicalVolume lV = new LogicalVolume(lVName, Integer.parseInt(size), vG);
            for (LogicalVolume lVTest: lVList) {
                if (lVTest.getName().equals(lVName)) {
                    lVExists = true;
                    System.out.println("error: Logical Volume name exists");
                }
            }
            if (!lVExists) {
                if (vG.availableSpace() - Integer.parseInt(size) < 0) {
                    System.out.println("error: there is not enough space in the Volume Group");
                } else {
                    vG.addLVList(lV);
                    lVList.add(lV);
                    System.out.println(vGName + " successfully installed");
                }
            }
        }
    }

    public void displayLVList() {
        for (int i = 0; i != lVList.size(); i++) {
            LogicalVolume lV = lVList.get(i);
            System.out.println(lV.getName() + ": [" + lV.getSize() + "] [" + lV.getVG().getName() + "] [" + lV.getUUID() + "]");
        }
    }

    public void load() {
        try {
            FileInputStream readPHDList = new FileInputStream("src/LVMSystem/PhysicalHardDriveList");
            ObjectInputStream readPHDListStream = new ObjectInputStream(readPHDList);
            Object pHDTest;
            while (true) {
                try {
                    pHDTest = readPHDListStream.readObject();
                    if (pHDTest instanceof PhysicalHardDrive) {
                        pHDList.add((PhysicalHardDrive) pHDTest);
                    } else {
                        System.out.println("Loading PHD error.");
                    }
                } catch (EOFException ex) {
                    break;
                }
            }
            readPHDList.close();
            readPHDListStream.close();

            FileInputStream readPHDListUsed = new FileInputStream("src/LVMSystem/PhysicalHardDriveListUsed");
            ObjectInputStream readPHDListUsedStream = new ObjectInputStream(readPHDListUsed);
            Object pHDUsedTest;
            while (true) {
                try {
                    pHDUsedTest = readPHDListUsedStream.readObject();
                    if (pHDUsedTest instanceof PhysicalHardDrive) {
                        pHDListUsed.add((PhysicalHardDrive) pHDUsedTest);
                    } else {
                        System.out.println("Loading PHD used error.");
                    }
                } catch (EOFException ex) {
                    break;
                }
            }
            readPHDListUsed.close();
            readPHDListUsedStream.close();

            FileInputStream readPVList = new FileInputStream("src/LVMSystem/PhysicalVolumeList");
            ObjectInputStream readPVListStream = new ObjectInputStream(readPVList);
            Object pVTest;
            while (true) {
                try {
                    pVTest = readPVListStream.readObject();
                    if (pVTest instanceof PhysicalVolume) {
                        pVList.add((PhysicalVolume) pVTest);
                    } else {
                        System.out.println("Loading PV error.");
                    }
                } catch (EOFException ex) {
                    break;
                }
            }
            readPVList.close();
            readPVListStream.close();

            FileInputStream readVGList = new FileInputStream("src/LVMSystem/VolumeGroupList");
            ObjectInputStream readVGListStream = new ObjectInputStream(readVGList);
            Object vGTest;
            while (true) {
                try {
                    vGTest = readVGListStream.readObject();
                    if (vGTest instanceof VolumeGroup) {
                        vGList.add((VolumeGroup) vGTest);
                    } else {
                        System.out.println("Loading VG error.");
                    }
                } catch (EOFException ex) {
                    break;
                }
            }
            readVGList.close();
            readVGListStream.close();

            FileInputStream readLVList = new FileInputStream("src/LVMSystem/LogicalVolumeList");
            ObjectInputStream readLVListStream = new ObjectInputStream(readLVList);
            Object lVTest;
            while (true) {
                try {
                    lVTest = readLVListStream.readObject();
                    if (lVTest instanceof LogicalVolume) {
                        lVList.add((LogicalVolume) lVTest);
                    } else {
                        System.out.println("Loading LV error.");
                    }
                } catch (EOFException ex) {
                    break;
                }
            }
            readLVList.close();
            readLVListStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void save() {
        try {
            FileOutputStream savePHDList = new FileOutputStream("src/LVMSystem/PhysicalHardDriveList");
            ObjectOutputStream savePHDListOut = new ObjectOutputStream(savePHDList);
            for (PhysicalHardDrive pHD: pHDList) {
                savePHDListOut.writeObject(pHD);
            }
            savePHDList.close();
            savePHDListOut.close();
            FileOutputStream savePHDListUsed = new FileOutputStream("src/LVMSystem/PhysicalHardDriveListUsed");
            ObjectOutputStream savePHDListUsedOut = new ObjectOutputStream(savePHDListUsed);
            for (PhysicalHardDrive pHDUsed: pHDListUsed) {
                savePHDListUsedOut.writeObject(pHDUsed);
            }
            savePHDListUsed.close();
            savePHDListUsedOut.close();
            FileOutputStream savePVList = new FileOutputStream("src/LVMSystem/PhysicalVolumeList");
            ObjectOutputStream savePVListOut = new ObjectOutputStream(savePVList);
            for (PhysicalVolume pV: pVList) {
                savePVListOut.writeObject(pV);
            }
            savePVList.close();
            savePVListOut.close();
            FileOutputStream saveVGList = new FileOutputStream("src/LVMSystem/VolumeGroupList");
            ObjectOutputStream saveVGListOut = new ObjectOutputStream(saveVGList);
            for (VolumeGroup vG: vGList) {
                saveVGListOut.writeObject(vG);
            }
            saveVGList.close();
            saveVGListOut.close();
            FileOutputStream saveLVList = new FileOutputStream("src/LVMSystem/LogicalVolumeList");
            ObjectOutputStream saveLVListOut = new ObjectOutputStream(saveLVList);
            for (LogicalVolume lV: lVList) {
                saveLVListOut.writeObject(lV);
            }
            saveLVList.close();
            saveLVListOut.close();
        }
        catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
    }
}
