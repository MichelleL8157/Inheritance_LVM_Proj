import java.util.ArrayList;

public class Commands {
    private String command;
    private ArrayList<PhysicalHardDrive> pHDList;

    public Commands(String command) {
        this.command = command;
        pHDList = new ArrayList<PhysicalHardDrive>();
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
            String leftOver = command.substring(10);
            String volName = leftOver.substring(0, leftOver.indexOf(" "));
            String size = leftOver.substring(volName.length(), leftOver.length() - 1);
            PhysicalVolume pV = new PhysicalVolume(volName);


        } else if (command.indexOf("vgcreate") == 0) {

        } else if (command.equals("vglist")) {

        } else if (command.equals("exit")) {

        } else {
            System.out.println("That is not in the list of your commands.");
        }
    }

    public void installDrive() {

    }
}
