package gameengine.utilities.timeformatting;


import static gameengine.utilities.timeformatting.TimeConversionFactor.*;

public class TimeFormatter {
    public static String format(long nanoseconds) {
        if (DAY.convert(nanoseconds) >= 1) {
            return DAY.format(nanoseconds);
        }
        if (HOUR.convert(nanoseconds) >= 1) {
            return HOUR.format(nanoseconds);
        }
        if (MINUTE.convert(nanoseconds) >= 1) {
            return MINUTE.format(nanoseconds);
        }
        if (SECOND.convert(nanoseconds) >= 1) {
            return SECOND.format(nanoseconds);
        }
        if (MILLISECOND.convert(nanoseconds) >= 1) {
            return MILLISECOND.format(nanoseconds);
        }
        if (MICROSECOND.convert(nanoseconds) >= 1) {
            return MICROSECOND.format(nanoseconds);
        }
        return NANOSECOND.format(nanoseconds);
    }

}
