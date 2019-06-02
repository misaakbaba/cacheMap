public class Line {
    private String tag;
    private int timeBit;
    private int validBit;
    private String data;
//    private int blockSize;

    public Line(String tag, int timeBit, int validBit, String data) {
        this.tag = tag;
        this.timeBit = timeBit;
        this.validBit = validBit;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public int getTimeBit() {
        return timeBit;
    }

    public int getValidBit() {
        return validBit;
    }

    public String getData() {
        return data;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTimeBit(short timeBit) {
        this.timeBit = timeBit;
    }

    public void setValidBit(byte validBit) {
        this.validBit = validBit;
    }

    public void setData(String data) {
        this.data = data;
    }
}
