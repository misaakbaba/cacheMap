import java.util.ArrayList;

public class Operations {
    void load(Traces trace, Cache L1D, Cache L2) {
        MemoryCalculation calculation = new MemoryCalculation();
        String addressBinary = calculation.hexToBinary(trace.getAddress());

        String temp = addressBinary.substring(0, addressBinary.length() - Main.L1B);
        int setIndexL1 = calculation.binaryToDecimal(temp.substring(temp.length() - Main.L1S));
        String tagL1 = temp.substring(0, temp.length() - Main.L1S);

        temp = addressBinary.substring(0, addressBinary.length() - Main.L2B);
        int setIndexL2 = calculation.binaryToDecimal(temp.substring(temp.length() - Main.L2S));
        String tagL2 = temp.substring(0, temp.length() - Main.L2S);

        boolean L1hit, L2hit;
        L1hit = calculation.cacheSearcher(L1D, setIndexL1, tagL1);
        L2hit = calculation.cacheSearcher(L2, setIndexL2, tagL2);
        System.out.println(trace.getOperation() + " " + trace.getAddress() + ", " + trace.getForwardByte());
        int addressIndex;
        if (L1hit) {
            System.out.print("L1D hit, ");
        } else {
            System.out.print("L1D miss, ");
        }
        if (L2hit) {
            System.out.println("L2 hit");
        } else {
            System.out.println("L2 miss");
        }
        if (!(L2hit || L1hit)) {
            System.out.print("Place in ");
        }
        if (!L2hit) {
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
            }
            addressIndex = Integer.parseInt(trace.getAddress(), 16);
            String data = calculation.memoryLoader(Main.ram, addressIndex, trace.getForwardByte(), (int) Math.pow(2, Main.L2B));
            L2.getCache().get(setIndexL2).addList(new Line(tagL2, Main.time, 1, data));
            Main.time++;

            if (Main.L2S == 0) {
                System.out.print("L2, ");
            } else {
                System.out.print("L2 set " + setIndexL2 + ", ");
            }
        }
        if (!L1hit) {
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
            }
            addressIndex = Integer.parseInt(trace.getAddress(), 16);
            String data = calculation.memoryLoader(Main.ram, addressIndex, trace.getForwardByte(), (int) Math.pow(2, Main.L1B));
            L1D.getCache().get(setIndexL1).addList(new Line(tagL1, Main.time, 1, data));
            Main.time++;
            if (Main.L1S == 0) {
                System.out.println("L1D");
            } else {
                System.out.println("L1D set " + setIndexL1);
            }
        }

    }
}
