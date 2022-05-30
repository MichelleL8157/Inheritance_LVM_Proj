import java.io.FileNotFoundException;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        Commands comm = new Commands();
        comm.load();
        System.out.print("Welcome to the LVM system! Enter your commands:\n\ncmd# ");
        String userInput = scan.nextLine();
        while (!userInput.equals("exit")) {
            comm.userCommand(userInput);
            System.out.print("\ncmd# ");
            userInput = scan.nextLine();
        }
        comm.save();
        System.out.println("Saving data. Good-bye!"); //yet to implement save
    }
}
