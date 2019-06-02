public class Traces {
    private char operation;
    private String address;
    private short forwardByte;
    private String data;

    public Traces(char operation, String address, short forwardByte) {
        this.operation = operation;
        this.address = address;
        this.forwardByte = forwardByte;
        this.data=null;
    }

    public Traces(char operation, String address, short forwardByte, String data) {
        this.operation = operation;
        this.address = address;
        this.forwardByte = forwardByte;
        this.data = data;
    }

    public char getOperation() {
        return operation;
    }

    public String getAddress() {
        return address;
    }

    public short getForwardByte() {
        return forwardByte;
    }

    public String getData() {
        return data;
    }
}
