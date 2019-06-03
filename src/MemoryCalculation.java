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
    int cacheIndexSearch(Cache cache,int setIndex,String tag){
        ArrayList<Line> lines = cache.getCache().get(setIndex).getLines();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getTag().equals(tag)) {
                return i;
            }
        }
        return 9999;
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

    void memoryChanger(String data, int startAddress, int byteSize) {
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

    void cacheChanger(Cache cache,int setIndex,int lineIndex, String newData, int startIndex, int endIndex) { //size kontrolü yap
        String data =cache.getCache().get(0).getLines().get(0).getData();
//        char[] dataArr=data.toCharArray();
//        for (int i = startIndex; i < endIndex; i++) {
//            dataArr[i]=' ';
//        }
//        String formatted=new String(dataArr);
        String temp = data.substring(startIndex, endIndex);
        data = data.replaceAll(temp, newData);
        cache.getCache().get(0).getLines().get(0).setData(data);
    }
}
