import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Welcome to the LVM system! Enter your commands:\n\ncmd# ");
        String userInput = scan.nextLine();
        Commands comm = new Commands();
        while (!userInput.equals("exit")) {
            comm.userCommand(userInput);
            System.out.print("\ncmd# ");
            userInput = scan.nextLine();
        }
        System.out.println("Good-bye!"); //yet to implement save
    }
}
