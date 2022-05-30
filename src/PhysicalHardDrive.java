import java.io.Serializable;

public class PhysicalHardDrive implements Serializable {
    private String name;
    private int size;

    public PhysicalHardDrive (String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() { return name; }
    public int getSize() { return size; }
}
