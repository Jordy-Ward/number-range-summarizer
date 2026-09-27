package numberrangesummarizer;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    private static final String INPUT_DELIMITER = ",";
    private static final String OUTPUT_DELIMITER = ", ";

    @Override
    public Collection<Integer> collect(String input) {
        if (input == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(input.split(INPUT_DELIMITER))
                .map(String::trim) //Assump 7.
                .filter(token -> !token.isEmpty()) //Assump 6
                .map(this::parseNonNegative) //Assump 4 .map(range -> parseNonNegative(range))
                .collect(Collectors.toList());
    }

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
