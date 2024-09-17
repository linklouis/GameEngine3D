package gameengine.timeformatting;

public enum TimeConversionFactor {
    DAY(8.64 * Math.pow(10, 13), new DaysFormatter()),
    HOUR(3.6 * Math.pow(10, 12), new HoursFormatter()),
    MINUTE(6 * Math.pow(10, 10), new MinutesFormatter()),
    SECOND(Math.pow(10, 9), new SecondsFormatter()),
    MILLISECOND(1000000, new MillisecondFormatter()),
    MICROSECOND(1000, new MicrosecondFormatter()),
    NANOSECOND(1, new NanosecondFormatter());

    private final double conversionFactor;
    private final Formatter formatter;

    TimeConversionFactor(double factor, Formatter formatter) {
        conversionFactor = factor;
        this.formatter = formatter;
    }

    public double convert(long nanos) {
        return nanos / conversionFactor;
    }

    public double convert(double nanos) {
        return nanos / conversionFactor;
    }

    double remainder(long nanos) {
        return nanos % conversionFactor;
    }

    double remainder(double nanos) {
        return nanos % conversionFactor;
    }

    public String getFormattedName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public String format(long nanos) {
        return formatter.format(nanos);
    }
}
