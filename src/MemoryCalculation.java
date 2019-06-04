import java.util.ArrayList;

public class MemoryCalculation {

    String hexToBinary(String hex) {// convert hex to binary
        return Integer.toBinaryString(Integer.parseInt(hex, 16));
    }

    String memoryLoader(ArrayList<String> ram, int index, int blockSize) {//take data from memory
        int blockBegin = index - (index % blockSize);
        String temp = "";
        for (int i = blockBegin; i < blockBegin + 8; i++) {
            temp = temp.concat(ram.get(i));
        }
        return temp;
    }

    boolean cacheSearcher(Cache cache, int setIndex, String tag) {//search data with set index and tag in cache, return true
        ArrayList<Line> lines = cache.getCache().get(setIndex).getLines();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getTag().equals(tag)) {
                return true;
            }
        }
        return false;
    }

    int cacheIndexSearch(Cache cache, int setIndex, String tag) { // search hit data index
        ArrayList<Line> lines = cache.getCache().get(setIndex).getLines();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getTag().equals(tag)) {
                return i;
            }
        }
        return 9999;
    }

    int binaryToDecimal(String n) {//convert binary to decimal
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

    void memoryChanger(String data, int startAddress, int byteSize) { //manipulate memory with given data
        ArrayList<String> ram = Main.ram;
        int dataSize = data.length() / 2;
        int index = 0;
        if (dataSize == byteSize) {
            for (int i = startAddress; i < startAddress + byteSize; i++) {
                ram.set(i, data.substring(index, index + 2));
                index += 2;
            }
        }
    }

    void cacheChanger(Cache cache, int setIndex, int lineIndex, String newData, int startIndex, int endIndex) { // manipulate cache with given data
        String data = cache.getCache().get(setIndex).getLines().get(lineIndex).getData();
        String temp = data.substring(startIndex, endIndex);
        data = data.replaceAll(temp, newData);
        cache.getCache().get(setIndex).getLines().get(lineIndex).setData(data);
    }
}
