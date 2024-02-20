package model;

import stats.BasicStats;
import stats.FullStats;
import stats.IStats;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Application {
    private final Configuration configuration;

    private final HashMap<EntryTypes, String> filenameByEntryType;

    private final IStats stats;

    public Application(Configuration configuration) {
        this.configuration = configuration;

        filenameByEntryType = new HashMap<>();
        filenameByEntryType.put(EntryTypes.Int, "integers.txt");
        filenameByEntryType.put(EntryTypes.Float, "floats.txt");
        filenameByEntryType.put(EntryTypes.String, "strings.txt");

        switch (configuration.getStatMode()) {
            case Short:
                stats = new BasicStats();
                break;
            case Full:
                stats = new FullStats();
                break;
            case No:
            default:
                stats = null;
        }
    }

    public void run() throws IOException {
        var outputFileByType = new HashMap<EntryTypes, File>();

        for (var inputPath : configuration.getInputPaths()) {
            var file = new File(inputPath);

            try (var br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    var type = getLineEntryType(line);
                    stats.update(line, type);
                    if (!outputFileByType.containsKey(type)) {
                        var name = configuration.getPrefix() == null
                                ? filenameByEntryType.get(type)
                                : configuration.getPrefix() + filenameByEntryType.get(type);
                        var path = configuration.getResultPath() == null
                                ? Path.of(name)
                                : Path.of(configuration.getResultPath(), name);

                        var outputFile = path.toFile();
                        if (configuration.getResultPath() != null)
                            outputFile.getParentFile().mkdirs();
                        outputFileByType.put(type, outputFile);
                        if (!configuration.isAppendMode()) {
                            try (var bw = new BufferedWriter(new FileWriter(outputFile, false))) {
                                bw.write("");
                            }
                        }
                    }

                    try (var bw = new BufferedWriter(new FileWriter(outputFileByType.get(type), true))) {
                        bw.write(line + "\n");
                    }
                }
            }
        }
    }

    public IStats getStats() {
        return stats;
    }

    private EntryTypes getLineEntryType(String line) {
        var intPattern = Pattern.compile("^-?[\\d `]+$");
        var intMatcher = intPattern.matcher(line);
        if (intMatcher.matches())
            return EntryTypes.Int;

        var floatPattern = Pattern.compile("^-?[\\d `]+[.,]\\d+([eE]-?\\d+)?$");
        var floatMatcher = floatPattern.matcher(line);
        if (floatMatcher.matches())
            return EntryTypes.Float;

        return EntryTypes.String;
    }
}
