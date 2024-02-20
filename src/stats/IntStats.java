package stats;

import model.EntryTypes;

public class IntStats implements IStats {
    private Double min = null;
    private Double max = null;
    private double sum = 0;
    private int count = 0;

    private Double average() {
        return count > 0.0 ? sum / count : null;
    }

    @Override
    public void update(String line, EntryTypes type) {
        if (type != EntryTypes.Int)
            return;

        try {
            var value = Double.parseDouble(line);

            if (min == null || value < min)
                min = value;
            if (max == null || max < value)
                max = value;
            sum += value;
            count++;
        } catch (NumberFormatException ex) {
            System.out.printf("Bad int, cannot parse it as double %s", ex);
        }
    }

    @Override
    public String toString() {
        return String.format("Integers; min: %s, max: %s, sum: %s, avg: %s", min, max, sum, average());
    }
}
