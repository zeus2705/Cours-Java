import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Command
{
    public String name();
    public Boolean run(java.util.Scanner scan);
}

class Quit implements Command
{

    public String name() {
        return "quit";
    }


    public Boolean run(java.util.Scanner scan) {
        return true;
    }
}
class Fibo implements Command
{

    public String name() {
        return "fibo";
    }


    public Boolean run(Scanner scan) {
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
        return true;
    }
}
class Freq implements Command
{

    public String name() {
        return "freq";
    }

    public Boolean run(Scanner scan) {
        System.out.println("Enter the path to the file:");
        String path = scan.nextLine();
        String file = "";
        try {
            file = java.nio.file.Files.readString(Paths.get(path));
        }
        catch (IOException e) {
            System.out.print(String.format("Unreadable file: %s",e.getClass().getName()));
            System.out.println();
            return false;
        }
        List<String> allword = Arrays.asList(file.replaceAll("(,|;|:|'|\n|.)", " ").toLowerCase().split(" "));
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
        return true;
    }
}

class Predict implements Command {
    @Override
    public String name() {
        return "predict";
    }

    @Override
    public Boolean run(Scanner scan) {
        System.out.println("Enter the path to the file:");
        String path = scan.nextLine();
        String file = "";
        try {
            file = java.nio.file.Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.out.print(String.format("Unreadable file: %s", e.getClass().getName()));
            System.out.println();
            return false;
        }
        List<String> allword = Arrays.asList(file.replaceAll("(,|;|:|'|\n)", " ").toLowerCase().split(" "));
        Map<String, String> nextword = new HashMap<>();
        for (String target : allword) {
            Map<String, Integer> freq = new HashMap<>();
            boolean nextoneok = false;
            for (String s : allword) {
                if (s.isBlank())
                    continue;
                if (nextoneok) {
                    Integer count = freq.get(s);
                    if (count == null)
                        count = 0;
                    freq.put(s, count + 1);
                    nextoneok = s.equals(target);
                }
                if (target.equals(s))
                    nextoneok = true;
            }
            for (int k = 0; k < 3; k++) {
                String best = "";
                long count = -1;
                for (Map.Entry<String, Integer> entry : freq.entrySet()) {
                    if (entry.getValue() > count) {
                        best = entry.getKey();
                        count = entry.getValue();
                    }
                }
                if (best.equals(""))
                    nextword.put(target, ".");
                else
                    nextword.put(target, best);
            }

        }
        System.out.println("Type a word from the text to start the prediction");
        String target = scan.nextLine();
        String[] prediction = new String[20];
        for (int i = 0; i < 20; i++) {
            boolean found = false;
            for (Map.Entry<String, String> entry : nextword.entrySet()) {
                if (entry.getKey().equals(target)) {
                    prediction[i] = entry.getValue();
                    target = prediction[i];
                    found = true;
                    break;
                }
            }
            if (!found && i == 0) {
                System.out.println("Your word was not in the list");
                return false;
            }
            if (prediction[i] == ".")
                break;
        }
        System.out.println(Arrays.stream(prediction).collect(Collectors.joining(" ")));
        return true;
    }
}

public class Launcher {

    public static java.util.Scanner scan;
    public static void main(String[] args){
        System.out.println("hi u can type command now");
        List<Command> avaiblecommmand = new ArrayList<>();
        avaiblecommmand.add(new Fibo());
        avaiblecommmand.add(new Quit());
        avaiblecommmand.add(new Freq());
        avaiblecommmand.add(new Predict());
        scan = new Scanner(System.in);
        String test = scan.nextLine();
        while (!test.equals("quit")) {
            for (Command c : avaiblecommmand)
            {
                if (c.name().equals(test) && c.run(scan))
                    return;
            }
            test = scan.nextLine();
        }
    }

}

