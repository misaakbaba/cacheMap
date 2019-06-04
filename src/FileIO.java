import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {
    ArrayList<Traces> readTrace(String path) throws FileNotFoundException {
        String temp = "";
        ArrayList<Traces> list = new ArrayList<>();
        Scanner scan = new Scanner(new File(path));
        while (scan.hasNextLine()) {
            temp = scan.nextLine();
            String formatted = temp.replaceAll(",", "");
//            System.out.println(formatted);
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

    void writeRam(ArrayList<String> ram, String path) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        for (int i = 0; i < ram.size(); i++) {
            writer.print(ram.get(i));
            writer.print(" ");
        }
        writer.close();
    }

    void writeCaches(Cache cache, String path) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        writer.println("tag       time        v    data");
        for (int i = 0; i < cache.getCache().size(); i++) {
            for (int j = 0; j < cache.getCache().get(i).getLines().size(); j++) {
                writer.println(cache.getCache().get(i).getLines().get(j).getTag() + "      " + cache.getCache().get(i).getLines().get(j).getTimeBit() + "       " +
                        cache.getCache().get(i).getLines().get(j).getValidBit() + "      " + cache.getCache().get(i).getLines().get(j).getData());
            }
            writer.println();
        }
        writer.close();
    }
}
