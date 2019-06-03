import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
 *parametreler ayarlanacak
 * hit miss eviction ayarlanacak
 * ram file io
 * testler
 * trace dosyasını yazdırmayı bırak
 * size 0 olanları yapma
 * debug
 */
public class Main {
    static int L1S = 0, L1E = 2, L1B = 3;
    static int L2S = 1, L2E = 2, L2B = 3;
    static ArrayList<String> ram;
    static int time = 1;
    static int L1IhitCt, L1IMissCt, L1IEviction;
    static int L1DhitCt, L1DMissCt, L1DEviction;
    static int L2hitCt, L2MissCt, L2Eviction;


    public static void main(String[] args) throws FileNotFoundException {
        Cache L1I = cacheCreater(L1S, L1E, L1B);
        Cache L1D = cacheCreater(L1S, L1E, L1B);
        Cache L2 = cacheCreater(L2S, L2E, L2B);

        Read read = new Read();
        String tracePath = "traces/test_small.trace";
        ram = read.readRam("ram.txt");
        ArrayList<Traces> traces = read.readTrace(tracePath);
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
    }

    static Cache cacheCreater(int set, int line, int block) {
        Cache cache = new Cache();
        for (int i = 0; i < set; i++) {
            cache.addList(new Set());
        }
        if (set == 0) {
            cache.addList(new Set());
        }
        return cache;
    }
}
