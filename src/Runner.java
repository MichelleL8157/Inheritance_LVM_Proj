import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Welcome to the LVM system! Enter your commands:\n\ncmd# ");
        String userInput = scan.nextLine();
        Commands comm = new Commands(userInput);

    }
}
