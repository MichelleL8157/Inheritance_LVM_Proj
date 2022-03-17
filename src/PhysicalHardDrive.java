public class PhysicalHardDrive {
    private String name;
    private int size;

    public PhysicalHardDrive(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() { return name; }
    public int getSize() { return size; }

}
/*
for (PhysicalHardDrive pV: super.getPHDList()) {
        boolean isThere = false;
        if (pV.getName().equals(name)) { isThere = true; }
        }
        if (isThere) {
        System.out.println("Error: " + name + " already exists");
        } else {
        this.name = name;
        this.size = size;
        super.addPHDList(new (name, size));
        System.out.println("Drive " + name + " installed");
        } */