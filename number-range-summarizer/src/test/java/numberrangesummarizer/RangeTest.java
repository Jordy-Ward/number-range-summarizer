package numberrangesummarizer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
    
class RangeTest {
    @Test
    void printsSingleNumberWhenStartEqualsEnd() {
        assertEquals("3", new Range(3, 3).toString());
    }

    @Test
    void printsStartAndEndSeparatedByDash() {
        assertEquals("6-8", new Range(6, 8).toString());
    }
}
