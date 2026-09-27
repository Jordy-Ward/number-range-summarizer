package numberrangesummarizer;

/**
 * Represents a group of numbers, of ranges.
 */
final class Range {
    private final int start;
    private final int end;

    Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**  
     *  Return the range as "start-end", or a single number when the start equals the end
     */
    @Override 
    public String toString() {
        return start == end ? String.valueOf(start) : start + "-" + end;
    }
}
