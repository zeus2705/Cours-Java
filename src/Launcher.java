import java.util.Scanner;

public class Launcher {

    public static java.util.Scanner scan;
    public static void main(String[] args){

        System.out.println("hi");
        scan = new Scanner(System.in);
        String test = scan.nextLine();
        while (!test.equals("quit")) {
            switch (test) {
                case "fibo" -> fibo();
                default -> System.out.println("Unknown command");
            }
            test = scan.nextLine();
        }
    }

    public static void fibo()
    {
        System.out.println("Enter a number N :");
        int n  = scan.nextInt();
        int i = n;
        scan.nextLine();
        long a = 0;
        long b = 1;
        while (i > 0)
        {
            long c = b;
            b = b + a;
            a = c;
            i--;
        }
        System.out.println(String.format("Fibo(%d)=%d",n,a));
    }
}
