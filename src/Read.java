import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Read {
    ArrayList<Traces> readTrace(String path) throws FileNotFoundException {
        String temp = "";
        ArrayList<Traces> list = new ArrayList<>();
        Scanner scan = new Scanner(new File(path));
        while (scan.hasNextLine()) {
            temp = scan.nextLine();
            String formatted = temp.replaceAll(",", "");
            System.out.println(formatted);
            list.add(parser(formatted));
        }
        scan.close();
        return list;
    }

    ArrayList<String> readRam(String path) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        Scanner scan = new Scanner(new File(path));
        while (scan.hasNext()) {
            list.add(scan.next());
        }
        scan.close();
        return list;
    }

    Traces parser(String s) {
        String formatted[] = s.split("\\s+");
        char operation = formatted[0].charAt(0);
        short forwardByte = Short.parseShort(formatted[2]);
        switch (formatted.length) {
            case 3:
                return new Traces(operation, formatted[1], forwardByte);
            case 4:
                return new Traces(operation, formatted[1], forwardByte, formatted[3]);
            default:
                return null;
        }
    }
}
