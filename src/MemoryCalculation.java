import java.util.ArrayList;

public class MemoryCalculation {

    String hexToBinary(String hex) {
        return Integer.toBinaryString(Integer.parseInt(hex, 16));
    }

    String memoryLoader(ArrayList<String> ram, int index, int offset, int blockSize) {
        int blockBegin = index - (index % blockSize);
        String temp = "";
        for (int i = blockBegin; i < blockBegin + 8; i++) {
            temp = temp.concat(ram.get(i));
        }
        return temp;
    }

    boolean cacheSearcher(Cache cache, int setIndex, String tag) {
        ArrayList<Line> lines = cache.getCache().get(setIndex).getLines();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getTag().equals(tag)) {
                return true;
            }
        }
        return false;
    }

    int binaryToDecimal(String n) {
        String num = n;
        int dec_value = 0;
        int base = 1;
        int len = num.length();
        for (int i = len - 1; i >= 0; i--) {
            if (num.charAt(i) == '1')
                dec_value += base;
            base = base * 2;
        }

        return dec_value;
    }
}
