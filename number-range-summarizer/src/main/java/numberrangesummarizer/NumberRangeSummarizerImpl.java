package numberrangesummarizer;

import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;


public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    @Override
    public Collection<Integer> collect(String input) {
        if (input == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(input.split(","))
                .map(n -> n.trim()) //Assump 7
                .filter(n -> !n.isEmpty()) //Assump 6
                .map(n -> parseNonNegative(n)) //Assump 4
                .collect(Collectors.toList());
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'summarizeCollection'");
    }

    private List<Range> groupIntoRanges(List<Integer> sorted) {
        ArrayList<Range> ranges = new ArrayList<>();
        int start = sorted.get(0);
        int end = start;
        int current;

        for (int i = 1; i < sorted.size(); i ++) {
            current = sorted.get(i);

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

    private Integer parseNonNegative(String v) {
        int number = Integer.parseInt(v);

        if (number < 0) {
            throw new IllegalArgumentException("Negative numbers cannot be collected in this implementation");
        }
        // else continue parsing the positive numbers
        return number;
    }
    
}
