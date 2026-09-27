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
                .map(n -> n.trim())
                .filter(n -> !n.isEmpty())
                .map(n -> parseNonNegative(n))
                .collect(Collectors.toList()); //take elements from stream and put them into a list of integers
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'summarizeCollection'");
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
