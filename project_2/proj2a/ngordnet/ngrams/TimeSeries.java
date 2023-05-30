package ngordnet.ngrams;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

    private TreeMap<Integer, Double> everything;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();

        for (int x: ts.keySet()) {
            if (startYear <= x && x <= endYear) {
                put(x, ts.get(x));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer> years = new ArrayList<>();
        for (int x: keySet()) {
            years.add(x);
        }
        return years;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> data = new ArrayList<>();
        for (double x : values()) {
            data.add(x);
        }
        return data;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries returnSeries = new TimeSeries();

        for (int year: this.years()) {
            if (!ts.containsKey(year)) {
                returnSeries.put(year, this.get(year));
            } else {
                double value = this.get(year) + ts.get(year);
                returnSeries.put(year, value);
            }
        }

        for (int year: ts.years()) {
            if (!this.containsKey(year)) {
                returnSeries.put(year, ts.get(year));
            }
        }

        return returnSeries;
    }
    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        for (int year: years()) {
            if (!ts.years().contains(year)) {
                throw new IllegalArgumentException();
            }
        }
        TimeSeries returnSeries = new TimeSeries();
        for (int year: years()) {
            double newVal = get(year) / ts.get(year);
            returnSeries.put(year, newVal);
        }
        return returnSeries;
    }

}
