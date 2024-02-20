package stats;

public class FullStats extends CompoundStats {
    public FullStats() {
        super.add(new BasicStats());
        super.add(new IntStats());
        super.add(new FloatStats());
        super.add(new StringStats());
    }
}
