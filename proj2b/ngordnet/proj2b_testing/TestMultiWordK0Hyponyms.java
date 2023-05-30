package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the case where the list of words is length greater than 1, but k is still zero. */
public class TestMultiWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    public static final String file1 = "data/ngrams/top_14377_words.csv";

    /** This is an example from the spec.*/
    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }

    // TODO: Add more unit tests (including edge case tests) here.

    @Test

    public void test1() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                file1, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("being");

        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 3);
        String actual = studentHandler.handle(nq);
        String expected = "[being, have, may]";
        assertThat(actual).isEqualTo(expected);

    }

    @Test

    public void test2() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                file1, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("abstraction");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 6);
        String actual = studentHandler.handle(nq);
        String expected = "[are, at, he, in, one, will]";
        assertThat(actual).isEqualTo(expected);


    }

    // TODO: Create similar unit test files for the k != 0 cases.
}
