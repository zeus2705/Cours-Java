import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Launcher {

    public static java.util.Scanner scan;
    public static void main(String[] args){
        System.out.println("hi");
        scan = new Scanner(System.in);
        String test = scan.nextLine();
        while (!test.equals("quit")) {
            switch (test) {
                case "fibo" -> fibo();
                case "freq" -> freq();
                default -> System.out.println("Unknown command");
            }
            test = scan.nextLine();
        }
    }
    public static void freq() {
        System.out.println("Enter the path to the file:");
        String path = scan.nextLine();
        String file = "";
        try {
            file = java.nio.file.Files.readString(Paths.get(path));
        }
        catch (IOException e) {
            System.out.print(String.format("Unreadable file: %s",e.getClass().getName()));
        }
        List<String> allword = Arrays.asList(file.replaceAll("(,|;|:|'|\n)", " ").toLowerCase().split(" "));
        Map<String, Integer> freqmap = new HashMap<>();
        for (String s: allword) {
            if (s.isBlank())
                continue;
            Integer count = freqmap.get(s);
            if (count == null)
                count = 0;
            freqmap.put(s, count + 1);
        }
        List<String> bestthree = new ArrayList<>();
        for (int k = 0; k < 3; k++) {
            String best = "";
            long count  = -1;
            for (Map.Entry<String, Integer> entry : freqmap.entrySet()) {
                if (entry.getValue() > count && !bestthree.contains(entry.getKey())) {
                    best = entry.getKey();
                    count = entry.getValue();
                }
            }
            if (!best.equals(""))
                bestthree.add(best);
        }
        for (int i = 0; i < bestthree.size(); i++) {
            if (i > 0)
                System.out.print(',');
            System.out.print(bestthree.get(i));
        }
        System.out.print('\n');
    }
    public static void fibo() {
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

