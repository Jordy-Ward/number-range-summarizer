# Number Range Summarizer

Produces a comma-delimited list of numbers, grouping sequential numbers into ranges.

```
Input:  "1,3,6,7,8,12,13,14,15,21,22,23,24,31"
Output: "1, 3, 6-8, 12-15, 21-24, 31"
```

## Tests
JUnit 5 (Jupiter) is used to write unit tests and Maven surefire is used to run them. As specified in the `pom.xml`.

Requires
JDK 17+ and Maven.

Run the tests from the `number-range-summarizer` dir.
```
mvn test
```

## Design and motivation

This implementation followed a Test Driven Development approach. TDD. Assumptions for the underlying problem were formalised first. JUnit tests were written to validate these assumptions and then the implementation was built to pass these JUnit tests. The result is the following maven project for a number range summarizer.

- `NumberRangeSummarizer`: the provided interface.
- `NumberRangeSummarizerImpl`: implements the interface.
  - `collect` only parses and validates the input string. Derived from assumptions.
  - `summarizeCollection` sorts, removes duplicates, groups and formats the collection.
- `Range`: immutable object to represent group of consecutive numbers. Only prints itself as `"3"` or `"6-8"` for example.

Sorting and checking for duplicates occur in `summarizeCollection` and not `collect`. This was a design choice. The collect method is seen as only a parsing method. To validate input and return the required `Collection` type.  Whereas in `summarizeCollection`, the actual range algorithm executes and so duplicates and sorting must be handled.


There is only one input format, so parsing and summarizing is kept to one class. If more formats were requested a dedicated parsing class would be more applicable. But in this case a single class is sufficient. 

**Complexity:** O(n log n) time, dominated by the sort. Grouping is a single O(n) pass.

## Streams and functional ideas

Java 8 streams bring ideas from functional languages such as Haskell into Java. `collect` reads almost like the Haskell equivalent:

```java
Arrays.stream(input.split(","))
        .map(String::trim)
        .filter(token -> !token.isEmpty())
        .map(this::parseNonNegative)
        .collect(Collectors.toList());
```

```haskell
collect = map parseNonNegative . filter (not . null) . map trim . splitOn ","
```

- **Declarative**: each step says *what* happens, not *how* it should happen.
- **Higher-order functions**: `map` and `filter` take functions as arguments (lambdas and method references), as in Haskell.
- **No mutation**: `summarizeCollection` streams into a new sorted list and never modifies the caller's collection.
- **Lazy evaluation**: nothing runs until the terminal operation (`collect(...)`), and each element flows through the whole pipeline before the next. That is why `"1, a, 3, 4.5"` fails at `a` without reaching `4.5`. Haskell is lazy by default.

**Where streams stop.** `groupIntoRanges` is a plain loop on purpose. Grouping depends on previous elements. When reading how to do this using streams I found the solutions verbose and rather overengineered. So a simple loop is clearer. It's simple and acts purely to facilitate an immutable range object.

## Assumptions

Each assumption is covered by a unit test in `NumberRangeSummarizerImplTest`.

| Case | Example | Behaviour |
|---|---|---|
| Unsorted input | `3,1,2` → `1-3` | Sorted in `summarizeCollection` |
| Duplicates | `1,1,2` → `1-2` | Ignored in `summarizeCollection` |
| Whitespace around numbers | `" 1 , 2,3 "` → `1-3` | Trimmed |
| Empty tokens | `",,2,3,"` → `2-3` | Skipped in `collect`|
| Null input | `null` → empty | `collect` returns an empty collection; `summarizeCollection` returns `""` |
| Empty or whitespace input | `""`, `"   "` → `""` | Empty in, empty out. Handled in collect|
| Negative numbers | `1,-2,3` | Rejected with `IllegalArgumentException`. In `parseNonNegative`. Output like `-3--1` would be ambiguous |
| Invalid tokens | `1,a,3, 4.5` | Rejected with `IllegalArgumentException`. In `parseNonNegative`. Only integers are accepted.|
| Numbers above `Integer.MAX_VALUE` | `2147483648` | Rejected with `IllegalArgumentException`. In `parseNonNegative`|

## Examples

| Input | Output |
|---|---|
| `1,3,5` | `1, 3, 5` |
| `1,2,3` | `1-3` |
| `3,4` | `3-4` |
| `4` | `4` |
