import java.util.Scanner;

public class Launcher {

    public static java.util.Scanner scan;
    public static void main(String[] args){

        System.out.println("hi");
        scan = new Scanner(System.in);
        while (!scan.nextLine().equals("quit"))
            System.out.println("Unknown command");
    }
}
