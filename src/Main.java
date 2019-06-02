import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    static int L1S = 0, L1E = 2, L1B = 3;
    static int L2S = 1, L2E = 2, L2B = 3;
    static ArrayList<String> ram;
    static int time=1;

    public static void main(String[] args) throws FileNotFoundException {
        Cache L1I = cacheCreater(L1S, L1E, L1B);
        Cache L1D = cacheCreater(L1S, L1E, L1B);
        Cache L2 = cacheCreater(L2S, L2E, L2B);

        String str1 = "123456789";
        String str2 = str1.substring(str1.length() - 2);
        String str3 = str1.substring(0, str1.length() - 2);

        System.out.println(str2);
        System.out.println(str3);
//        L1I.addList(new Set());
//        L1I.getCache().get(0).addList(new Line("000000", 1, 1, "123456"));
//        System.out.println("l1 I size " + L1I.getCache().size());
//        System.out.println("l1 I data" + L1I.getCache().get(0).getLines().get(0).getData());
//        System.out.println("l1 I tag " + L1I.getCache().get(0).getLines().get(0).getTag());
//        System.out.println("l1 I time " + L1I.getCache().get(0).getLines().get(0).getTimeBit());
        MemoryCalculation calculation = new MemoryCalculation();
        Read read = new Read();
        String tracePath = "traces/test_small.trace";
        ram = read.readRam("ram.txt");
        ArrayList<Traces> traces = read.readTrace(tracePath);
        Operations operations=new Operations();
        for (int i = 0; i < traces.size(); i++) {
            switch (traces.get(i).getOperation()) {
                case 'L':
                    operations.load(traces.get(i),L1D,L2);
                    break;
                case 'I':
                    break;
                case 'S':
                    break;
                case 'M':
                    break;
                default:
                    break;
            }
        }

//        String data=calculation.memoryLoader(ram,3,2,8);
//        System.out.println(data);

//        System.out.println("size is: " + ram.size());
//        String str = "Hello I'm your String";
//        String[] splited = str.split("\\s+");
//        for (int i = 0; i < splited.length; i++) {
//            System.out.println(splited[i]);
//        }
//        System.out.println("traces");
//        for (int i = 0; i < traces.size(); i++) {
//            System.out.println(traces.get(i).getOperation()+"-"+traces.get(i).getAddress()+"-"+traces.get(i).getForwardByte());
//        }

    }

    static Cache cacheCreater(int set, int line, int block) {
        Cache cache = new Cache();
        for (int i = 0; i < set; i++) {
            cache.addList(new Set());
        }
        if (set==0){
            cache.addList(new Set());
        }
        System.out.println(cache.getCache().size());
        return cache;
    }
}
