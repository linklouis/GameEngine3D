package gameengine.utilities.timeformatting;


import static gameengine.utilities.timeformatting.TimeConversionFactor.NANOSECOND;

public class NanosecondFormatter implements Formatter {
    @Override
    public String format(long nanoseconds) {
        return Formatter.formatOneTimeStep(nanoseconds, NANOSECOND, false);
    }
}
