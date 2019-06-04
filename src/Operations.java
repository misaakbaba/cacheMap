import java.util.ArrayList;

public class Operations {
    void load(Traces trace, Cache L1D, Cache L2) { // for load and instruction load
        MemoryCalculation calculation = new MemoryCalculation();
        if (Integer.parseInt(trace.getAddress(), 16) == 0 || trace.getForwardByte() == 0) { // if address and size is equal to zero return method
            return;
        }
        String addressBinary = calculation.hexToBinary(trace.getAddress());

        String temp = addressBinary.substring(0, addressBinary.length() - Main.L1B); // subtract block offset
        int setIndexL1 = calculation.binaryToDecimal(temp.substring(temp.length() - Main.L1S)); // find set index
        String tagL1 = temp.substring(0, temp.length() - Main.L1S); //find tag

        temp = addressBinary.substring(0, addressBinary.length() - Main.L2B);
        int setIndexL2 = calculation.binaryToDecimal(temp.substring(temp.length() - Main.L2S));
        String tagL2 = temp.substring(0, temp.length() - Main.L2S);

        boolean L1hit, L2hit; //hit situation
        L1hit = calculation.cacheSearcher(L1D, setIndexL1, tagL1); //determine hit or miss
        L2hit = calculation.cacheSearcher(L2, setIndexL2, tagL2);
        System.out.println(trace.getOperation() + " " + trace.getAddress() + ", " + trace.getForwardByte());
        int addressIndex;
        if (L1hit) {// hit condition
            if (trace.getOperation() == 'L' || trace.getOperation() == 'M') {
                System.out.print("L1D hit, ");
                Main.L1DhitCt++;
            } else {
                System.out.print("L1I hit, ");
                Main.L1IhitCt++;
            }
        } else { // miss condition
            if (trace.getOperation() == 'L' || trace.getOperation() == 'M') {
                System.out.print("L1D miss, ");
                Main.L1DMissCt++;
            } else {
                System.out.print("L1I miss, ");
                Main.L1IMissCt++;
            }
        }
        if (L2hit) { // hit condition
            System.out.println("L2 hit");
            Main.L2hitCt++;
        } else { //miss condition
            System.out.println("L2 miss");
            Main.L2MissCt++;
        }
        if (!(L2hit || L1hit)) {
            System.out.print("Place in ");
        }
        if (!L2hit) { // L2 eviction situation
            if (L2.getCache().get(setIndexL2).getLines().size() >= Main.L2E) {
                ArrayList<Line> lines = L2.getCache().get(setIndexL2).getLines();
                int min = 99999, index = 0;
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).getTimeBit() < min) {
                        min = lines.get(i).getTimeBit();
                        index = i;
                    }
                }
                lines.remove(index);
                Main.L2Eviction++;
            }
            addressIndex = Integer.parseInt(trace.getAddress(), 16);
            String data = calculation.memoryLoader(Main.ram, addressIndex, (int) Math.pow(2, Main.L2B));
            L2.getCache().get(setIndexL2).addList(new Line(tagL2, Main.time, 1, data));
            Main.time++;

            if (Main.L2S == 0) {
                System.out.print("L2, ");
            } else {
                System.out.print("L2 set " + setIndexL2 + ", ");
            }
        }
        if (!L1hit) {// L1 eviction situation
            if (L1D.getCache().get(setIndexL1).getLines().size() >= Main.L1E) {
                ArrayList<Line> lines = L1D.getCache().get(setIndexL2).getLines();
                int min = 99999, index = 0;
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).getTimeBit() < min) {
                        min = lines.get(i).getTimeBit();
                        index = i;
                    }
                }
                lines.remove(index);
                if (trace.getOperation() == 'L' || trace.getOperation() == 'M') {
                    Main.L1DEviction++;
                } else {
                    Main.L1IEviction++;
                }
            }
            addressIndex = Integer.parseInt(trace.getAddress(), 16);
            String data = calculation.memoryLoader(Main.ram, addressIndex, (int) Math.pow(2, Main.L1B));
            L1D.getCache().get(setIndexL1).addList(new Line(tagL1, Main.time, 1, data));
            Main.time++;
            if (Main.L1S == 0) {
                System.out.println("L1D");
            } else {
                System.out.println("L1D set " + setIndexL1);
            }
        }
    }

    void store(Traces trace, Cache L1D, Cache L2) {// store case
        MemoryCalculation calculation = new MemoryCalculation();
        if (Integer.parseInt(trace.getAddress(), 16) == 0 || trace.getForwardByte() == 0) {
            return;
        }
        String addressBinary = calculation.hexToBinary(trace.getAddress());

        String temp = addressBinary.substring(0, addressBinary.length() - Main.L1B);
        int setIndexL1 = calculation.binaryToDecimal(temp.substring(temp.length() - Main.L1S));
        String tagL1 = temp.substring(0, temp.length() - Main.L1S);

        temp = addressBinary.substring(0, addressBinary.length() - Main.L2B);
        int setIndexL2 = calculation.binaryToDecimal(temp.substring(temp.length() - Main.L2S));
        String tagL2 = temp.substring(0, temp.length() - Main.L2S);

        boolean L1hit, L2hit;
        L1hit = calculation.cacheSearcher(L1D, setIndexL1, tagL1); //determine hit or miss
        L2hit = calculation.cacheSearcher(L2, setIndexL2, tagL2);
        System.out.println(trace.getOperation() + " " + trace.getAddress() + " " + trace.getForwardByte() + " " + trace.getData());
        if (L1hit) { // hit situation
            System.out.print("L1D hit, ");
            Main.L1DhitCt++;
        } else {// miss
            System.out.print("L1D miss, ");
            Main.L1DMissCt++;
        }
        if (L2hit) { // hit
            System.out.println("L2 hit");
            Main.L2hitCt++;
        } else { // miss
            System.out.println("L2 miss");
            Main.L2MissCt++;
        }
        System.out.print("Store in ");
        if (L1hit) { // hit condition change cache
            System.out.print("L1D, ");
            int index = calculation.cacheIndexSearch(L1D, setIndexL1, tagL1);
            int startIndex = Integer.parseInt(trace.getAddress(), 16) % (int) Math.pow(2, Main.L1B);
            calculation.cacheChanger(L1D, setIndexL1, index, trace.getData(), startIndex, (startIndex + trace.getForwardByte() * 2));
        }
        if (L2hit) { //hit condition change cache
            System.out.print("L2, ");
            int index = calculation.cacheIndexSearch(L2, setIndexL2, tagL2);
            int startIndex = Integer.parseInt(trace.getAddress(), 16) % (int) Math.pow(2, Main.L2B);
            calculation.cacheChanger(L2, setIndexL2, index, trace.getData(), startIndex, (startIndex + trace.getForwardByte() * 2));
        }
        calculation.memoryChanger(trace.getData(), Integer.parseInt(trace.getAddress(), 16), trace.getForwardByte());//change ram
        System.out.println("RAM");
    }
}
