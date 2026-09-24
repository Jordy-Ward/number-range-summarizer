package numberrangesummarizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

class NumberRangeSummarizerImplTest {
    
    //hold the summarizer as the interface type
    private final NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();
    
    @Test
    void summarizesSampleInput() {
        Collection<Integer> numbers = summarizer.collect("1,3,6,7,8,12,13,14,15,21,22,23,24,31");
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", summarizer.summarizeCollection(numbers));
    }

    @Test
    void summarizeSortsUnsortedInput() {
        assertEquals("1-4, 10", summarizer.summarizeCollection(Arrays.asList(3, 1, 10, 2, 4)));
    }

    @Test
    void summarizeIgnoresDuplicates() {
        assertEquals("1-2, 4, 6-7", summarizer.summarizeCollection(Arrays.asList(1, 1, 2, 4, 6, 7, 7)));
    }

    @Test
    void rejectsNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("1,-2,3"));
    }

    @Test
    void rejectsInvalidTokens() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("1, a, 3, 5"));
    }

    @Test
    void collectReturnsEmptyForNullInput() {
        assertTrue(summarizer.collect(null).isEmpty());
    }

    @Test
    void summarizeCollectionReturnsEmptyForNullInput() {
        assertEquals("", summarizer.summarizeCollection(null));
    }

    @Test
    void handlesEmptyInputAsEmptyOutput() {
        Collection<Integer> numbers = summarizer.collect("");
        assertEquals("", summarizer.summarizeCollection(numbers));
    }

    @Test
    void handlesWhiteSpaceOnlyAsEmptyOutput() {
        Collection<Integer> numbers = summarizer.collect("   ");
        assertEquals("", summarizer.summarizeCollection(numbers));
    }

    @Test
    void trimsWhiteSpaceInInput() {
        Collection<Integer> numbers = summarizer.collect("1, 3, 6,  7 ,8,12,13,  14,15,21,  22,23,24,31");
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", summarizer.summarizeCollection(numbers));
    }

    @Test
    void rejectsNumbersLargerThanMaxInteger() {
        assertThrows(IllegalArgumentException.class, () -> summarizer.collect("2,2147483648"));
    }

    @Test
    void skipsEmptyTokens() {
        Collection<Integer> numbers = summarizer.collect(",,2,3,,4,6,10,");
        assertEquals("2-4, 6, 10", summarizer.summarizeCollection(numbers));
    }

    @Test
    void returnsNumbersUnchangedForNoRangeInInput() {
        Collection<Integer> numbers = summarizer.collect("1,4,6,10");
        assertEquals("1, 4, 6, 10", summarizer.summarizeCollection(numbers));
    }

    @Test
    void summarizesSingleRange() {
        Collection<Integer> numbers = summarizer.collect("1,2,3");
        assertEquals("1-3", summarizer.summarizeCollection(numbers));
    }

    @Test
    void returnsTwoConsecutiveNumbersAsRange() {
        Collection<Integer> numbers = summarizer.collect("3,4");
        assertEquals("3-4", summarizer.summarizeCollection(numbers));
    }

    @Test
    void returnsSingleNumberUnchanged() {
        Collection<Integer> numbers = summarizer.collect("3");
        assertEquals("3", summarizer.summarizeCollection(numbers));
    }
}
