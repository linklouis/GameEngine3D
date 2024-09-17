package gameengine.timeformatting;

import static gameengine.timeformatting.TimeConversionFactor.SECOND;

public class SecondsFormatter implements Formatter {
    @Override
    public String format(long nanoseconds) {
        return Formatter.formatOneTimeStep(nanoseconds, SECOND, false);
    }
}
