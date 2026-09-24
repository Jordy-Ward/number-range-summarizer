# Assumptions and testing thoughts

### Unsorted lists
Sort in summarizeCollection dont error

### Duplicates
Ignore duplicates don't error. Done in summarizeCollection

### Negatives
Reject negatives. Ambigious -3--1 input output

### Invalid tokens
Reject invalid tokens 'a'

### Null / emtpy input
Empty result. Empty in equals empty out for both interface methods

### Whitespace in input
Trim

### Max integer
Catch n + 1 over flow error. where n Integer.MAX_VALUE

### Empty tokens
Just skip

### No ranges in input
1,3,5 -> 1, 3, 5

### One range in input
3,4,5,6 - > 3-6

### a range of two numbers
1,2 -> 1-2

### Single number single output
4 - > 4

### Whitespace only in input
Should give empty output "    " -> ""

