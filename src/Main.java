import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    static int L1S, L1E, L1B;
    static int L2S, L2E, L2B;
    static ArrayList<String> ram;
    static int time = 1;
    static int L1IhitCt, L1IMissCt, L1IEviction;
    static int L1DhitCt, L1DMissCt, L1DEviction;
    static int L2hitCt, L2MissCt, L2Eviction;
    static String tracePath = "";

    public static void main(String[] args) throws FileNotFoundException {
        argSet(args);
        Cache L1I = cacheCreater((int) Math.pow(2, L1S), L1E, L1B);
        Cache L1D = cacheCreater((int) Math.pow(2, L1S), L1E, L1B);
        Cache L2 = cacheCreater((int) Math.pow(2, L2S), L2E, L2B);

        FileIO fileIO = new FileIO();
        ram = fileIO.readRam("ram.txt");
        ArrayList<Traces> traces = fileIO.readTrace(tracePath);
        Operations operations = new Operations();
        for (int i = 0; i < traces.size(); i++) {
            switch (traces.get(i).getOperation()) {
                case 'L':
                    operations.load(traces.get(i), L1D, L2);
                    break;
                case 'I':
                    operations.load(traces.get(i), L1I, L2);
                    break;
                case 'S':
                    operations.store(traces.get(i), L1D, L2);
                    break;
                case 'M':
                    operations.load(traces.get(i), L1D, L2);
                    operations.store(traces.get(i), L1D, L2);
                    break;
                default:
                    break;
            }
        }
        System.out.println();
        System.out.println("L1I-hits:" + Main.L1IhitCt + " L1I-misses:" + Main.L1IMissCt +
                " L1I-evictions:" + Main.L1IEviction);
        System.out.println("L1D-hits:" + Main.L1DhitCt + " L1D-misses:" + Main.L1DMissCt +
                " L1D-evictions:" + Main.L1DEviction);
        System.out.println("L2-hits:" + Main.L2hitCt + " L2-misses:" + Main.L2MissCt +
                " L2-evictions:" + Main.L2Eviction);

//        System.out.println(Integer.parseInt("00000000",16));
        fileIO.writeRam(ram, "newRam.txt");
        fileIO.writeCaches(L1D,"L1D.txt");
        fileIO.writeCaches(L1I,"L1I.txt");
        fileIO.writeCaches(L2,"L2.txt");

    }

    static Cache cacheCreater(int set, int line, int block) {
        Cache cache = new Cache();
        for (int i = 0; i < set; i++) {
            cache.addList(new Set());
        }
        if (set == 1) {
            cache.addList(new Set());
        }
        return cache;
    }

    static void argSet(String[] args) {
        int pass = 0;
        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-L1s":
                        Main.L1S = Integer.parseInt(args[i + 1]);
                        pass++;
                        break;
                    case "-L1E":
                        Main.L1E = Integer.parseInt(args[i + 1]);
                        pass++;
                        break;
                    case "-L1b":
                        Main.L1B = Integer.parseInt(args[i + 1]);
                        pass++;
                        break;
                    case "-L2s":
                        Main.L2S = Integer.parseInt(args[i + 1]);
                        pass++;
                        break;
                    case "-L2E":
                        Main.L2E = Integer.parseInt(args[i + 1]);
                        pass++;
                        break;
                    case "-L2b":
                        Main.L2B = Integer.parseInt(args[i + 1]);
                        pass++;
                        break;
                    case "-t":
                        Main.tracePath = args[i + 1];
                        pass++;
                        break;
                }
            }
            if (pass != 7) {
                System.out.println("Missing Argument, re-run Code");
                System.exit(1);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("invalid arguman attempt, please re-run code");
            System.exit(1);
        }
    }
}
