package numberrangesummarizer;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Summarizes non negative integers into comma separated ranges
 */
public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    private static final String INPUT_DELIMITER = ",";
    private static final String OUTPUT_DELIMITER = ", ";

    /**
     * Parses comma separated string of non negative integers.
     * Whitespace around numbers is trimmed and empty tokens are skipped
     *
     * @param input comma separated numbers, "1, 2, 3" and can be null
     * @return the parsed numbers in input order, or an empty collection if input is null
     * @throws IllegalArgumentException if a token is negative, not an integer, or too large for an int type
     */
    @Override
    public Collection<Integer> collect(String input) {
        if (input == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(input.split(INPUT_DELIMITER))
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .map(this::parseNonNegative)
                .collect(Collectors.toList());
    }

    /**
     * Sorts the numbers, ignores duplicates and groups consecutive numbers into ranges.
     * The given collection is not modified.
     *
     * @param input the numbers to summarize, in any order. Can contain duplicates or be null
     * @return the ranges joined by ", ", or "" if input is null or empty
     */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        List<Integer> sorted = input.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        return groupIntoRanges(sorted).stream()
                .map(Range::toString)
                .collect(Collectors.joining(OUTPUT_DELIMITER));
    }

    /**
     * @param sorted a non empty list, sorted ascending with no duplicates
     */
    private List<Range> groupIntoRanges(List<Integer> sorted) {
        List<Range> ranges = new ArrayList<>();
        int start = sorted.get(0);
        int end = start;

        for (int i = 1; i < sorted.size(); i++) {
            int current = sorted.get(i);

            if (current == end + 1) { //within range
                end = current;
            } else { //range for start var has ended. add
                ranges.add(new Range(start, end));
                start = current;
                end = current;
            }
        }
        ranges.add(new Range(start, end));
        return ranges;
    }

    private int parseNonNegative(String token) {
        int number = Integer.parseInt(token);

        if (number < 0) {
            throw new IllegalArgumentException("Negative numbers cannot be collected in this implementation: " + token);
        }
        return number;
    }
}
