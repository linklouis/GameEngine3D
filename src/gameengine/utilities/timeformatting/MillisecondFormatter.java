package gameengine.utilities.timeformatting;


import static gameengine.utilities.timeformatting.TimeConversionFactor.MILLISECOND;

public class MillisecondFormatter implements Formatter {
    @Override
    public String format(long nanoseconds) {
        return Formatter.formatOneTimeStep(nanoseconds, MILLISECOND, false);
    }
}
