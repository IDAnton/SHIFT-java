package stats;

import model.EntryTypes;

import java.util.HashMap;
import java.util.Optional;

public class BasicStats implements IStats {
    private final HashMap<EntryTypes, Integer> countByType = new HashMap<>();

    @Override
    public void update(String line, EntryTypes type) {
        var oldValue = Optional.ofNullable(countByType.get(type)).orElse(0);
        countByType.put(type, oldValue + 1);
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();

        for (var typeAndCount: countByType.entrySet())
            stringBuilder.append(typeAndCount.getKey()).append(": ").append(typeAndCount.getValue()).append(", ");

        return stringBuilder.toString();
    }
}
