package stats;

import model.EntryTypes;

public interface IStats {
    void update(String line, EntryTypes type);

    String toString();
}
