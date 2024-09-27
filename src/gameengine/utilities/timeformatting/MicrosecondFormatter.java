package gameengine.utilities.timeformatting;

import static gameengine.utilities.timeformatting.TimeConversionFactor.MICROSECOND;

public class MicrosecondFormatter implements Formatter {
    @Override
    public String format(long nanoseconds) {
        return Formatter.formatOneTimeStep(nanoseconds, MICROSECOND, false);
    }
}
