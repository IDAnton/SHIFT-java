package stats;

import model.EntryTypes;

import java.util.ArrayList;

public class CompoundStats implements IStats {
    private final ArrayList<IStats> children = new ArrayList<>();

    @Override
    public void update(String line, EntryTypes type) {
        for (var child : children)
            child.update(line, type);
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        
        for (var child : children)
            stringBuilder.append(child).append('\n');

        return stringBuilder.toString();
    }

    public void add(IStats child) {
        children.add(child);
    }
}
