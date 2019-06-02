import java.util.ArrayList;

public class Set {
    private ArrayList<Line> lines;
//    private int setNumber;


    public Set() {
        lines=new ArrayList<>();
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void addList(Line line) {
       this.lines.add(line);
    }
}
