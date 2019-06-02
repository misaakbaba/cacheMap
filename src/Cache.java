import java.util.ArrayList;

public class Cache {
    private ArrayList<Set> cache;

    public Cache() {
        cache=new ArrayList<>();
    }

    public ArrayList<Set> getCache() {
        return cache;
    }

    public void addList(Set set) {
        this.cache.add(set);
    }
}
