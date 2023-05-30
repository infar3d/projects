package ngordnet.ngrams;


import java.util.Collection;
import java.util.TreeMap;

import edu.princeton.cs.algs4.In;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    private TimeSeries countMap;
    private TreeMap<String, TimeSeries> wordMap;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In words = new In(wordsFilename);
        In counts = new In(countsFilename);
        countMap = new TimeSeries();
        wordMap = new TreeMap();

        /** INPUTTING COUNTS **/ // @geeksforgeeks webpage on how to use .split() :
        // https://www.geeksforgeeks.org/split-string-java-examples/
        while (counts.hasNextLine()) {
            String[] currLine = counts.readLine().split(",");
            int year = Integer.parseInt(currLine[0]);
            double count = Double.parseDouble(currLine[1]);
            countMap.put(year, count);
        }

        /** INPUTTING WORDS **/
        while (words.hasNextLine()) {
            String[] currLine = words.readLine().split("\t");
            String word = currLine[0];
            int year = Integer.parseInt(currLine[1]);
            double count = Double.parseDouble(currLine[2]);

            if (!wordMap.containsKey(word)) {
                TimeSeries nums = new TimeSeries();
                nums.put(year, count);
                wordMap.put(word, nums);
            } else {
                wordMap.get(word).put(year, count);
            }
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries returnSeries = new TimeSeries();
        TimeSeries wordSeries = wordMap.get(word);
        for (int year: wordSeries.keySet()) {
            if (startYear <= year && year <= endYear) {
                returnSeries.put(year, wordSeries.get(year));
            }
        }
        return returnSeries;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries returnSeries = new TimeSeries();
        TimeSeries wordSeries = wordMap.get(word);
        for (int year: wordSeries.keySet()) {
            returnSeries.put(year, wordSeries.get(year));
        }
        return returnSeries;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return countMap;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries returnSeries = new TimeSeries();
        TimeSeries wordSeries = wordMap.get(word);
        if (wordSeries == null) {
            return returnSeries;
        } else {
            for (int year: wordSeries.keySet()) {
                if (startYear <= year && year <= endYear) {
                    double val = wordSeries.get(year);
                    double totalWords = countMap.get(year);
                    returnSeries.put(year, val / totalWords);
                }
            }
            return returnSeries;
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries returnSeries = new TimeSeries();
        TimeSeries wordSeries = wordMap.get(word);

        if (wordSeries == null) {
            return returnSeries;
        } else {
            for (int year: wordSeries.keySet()) {
                double val = wordSeries.get(year);
                double totalWords = countMap.get(year);
                returnSeries.put(year, val / totalWords);
            }
            return returnSeries;
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries returnSeries = new TimeSeries();
        for (String word: words) {
            TimeSeries weightedWords = weightHistory(word, startYear, endYear);
            returnSeries = returnSeries.plus(weightedWords);
        }
        return returnSeries;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries returnSeries = new TimeSeries();
        for (String word: words) {
            TimeSeries weightedWords = weightHistory(word);
            returnSeries = returnSeries.plus(weightedWords);
        }
        return returnSeries;
    }
}
