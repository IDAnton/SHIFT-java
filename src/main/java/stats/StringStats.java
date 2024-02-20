package stats;

import model.EntryTypes;

public class StringStats implements IStats {
    public int count = 0;
    public Integer minLen = null;
    public Integer maxLen = null;

    @Override
    public void update(String line, EntryTypes type) {
        if (type != EntryTypes.String)
            return;

        var length = line.length();

        if (minLen == null || length < minLen)
            minLen = length;
        if (maxLen == null || maxLen < length)
            maxLen = length;
        count++;
    }

    @Override
    public String toString() {
        return String.format("Strings; count: %s, minLen: %s, maxLen: %s", count, minLen, maxLen);
    }
}
