package model;

import stats.StatModes;

import java.util.List;

public class Configuration {
    private final String resultPath;

    private final String prefix;

    private final boolean isAppendMode;

    private final StatModes statMode;

    private final List<String> inputPaths;

    public Configuration(String resultPath, String prefix, boolean isAppendMode, StatModes statMode, List<String> inputPaths) {
        this.resultPath = resultPath;
        this.prefix = prefix;
        this.isAppendMode = isAppendMode;
        this.statMode = statMode;
        this.inputPaths = inputPaths;
    }

    public String getResultPath() {
        return resultPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppendMode() {
        return isAppendMode;
    }

    public StatModes getStatMode() {
        return statMode;
    }

    public List<String> getInputPaths() {
        return inputPaths;
    }
}
