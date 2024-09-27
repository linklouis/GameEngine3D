package gameengine.utilities.timeformatting;

import static gameengine.utilities.timeformatting.TimeConversionFactor.SECOND;

public class SecondsFormatter implements Formatter {
    @Override
    public String format(long nanoseconds) {
        return Formatter.formatOneTimeStep(nanoseconds, SECOND, false);
    }
}
