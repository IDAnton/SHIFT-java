import model.Application;
import model.Configuration;
import stats.StatModes;

import java.io.IOException;
import java.util.*;

public class Main {
    private enum ArgParsingStatus {
        UnqualifiedReading,
        ReadingResultPath,
        ReadingPrefix
    }

    private static Configuration configurationFromArgs(List<String> args) throws MissingFormatArgumentException {
        final var resultPathOption = "-o";
        final var prefixOption = "-p";
        final var appendModeOption = "-a";
        final var shortStatsOption = "-s";
        final var fullStatsOption = "-f";

        String resultPath = null;
        String prefix = null;
        boolean isAppendMode = false;
        StatModes statMode = StatModes.No;
        var inputFiles = new ArrayList<String>();

        var status = ArgParsingStatus.UnqualifiedReading;
        for (String arg : args) {
            switch (status) {
                case UnqualifiedReading:
                    if (arg.equals(appendModeOption)) {
                        isAppendMode = true;
                        break;
                    }
                    if (arg.equals(shortStatsOption)) {
                        statMode = StatModes.Short;
                        break;
                    }
                    if (arg.equals(fullStatsOption)) {
                        statMode = StatModes.Full;
                        break;
                    }
                    if (arg.equals(resultPathOption)) {
                        status = ArgParsingStatus.ReadingResultPath;
                        break;
                    }
                    if (arg.equals(prefixOption)) {
                        status = ArgParsingStatus.ReadingPrefix;
                        break;
                    }
                    if (arg.startsWith("-"))
                        throw new MissingFormatArgumentException("Bad option");
                    inputFiles.add(arg);
                    break;
                case ReadingResultPath:
                    if (arg.startsWith("-"))
                        throw new MissingFormatArgumentException("Expected value after option");
                    resultPath = arg;
                    status = ArgParsingStatus.UnqualifiedReading;
                    break;
                case ReadingPrefix:
                    if (arg.startsWith("-"))
                        throw new MissingFormatArgumentException("Expected value after option");
                    prefix = arg;
                    status = ArgParsingStatus.UnqualifiedReading;
                    break;
            }
        }
        if (status != ArgParsingStatus.UnqualifiedReading)
            throw new MissingFormatArgumentException("Expected value after option");

        return new Configuration(resultPath, prefix, isAppendMode, statMode, inputFiles);
    }

    public static void main(String[] args) {
        try {
            var configuration = configurationFromArgs(List.of(args));
            var application = new Application(configuration);

            application.run();

            System.out.println(application.getStats());
        }
        catch (MissingFormatArgumentException e) {
            System.out.printf("Bad console parameters: %s%n", e.getMessage());
        }
        catch (IOException e) {
            System.out.printf("Bad input: %s%n", e.getMessage());
        }
        catch (Exception e) {
            System.out.printf("Unexpected error: %s%n", e.getMessage());
        }
    }
}