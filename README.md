# 1. Assumptions and testing thoughts

### 2. Unsorted lists
Sort in summarizeCollection dont error

###  3. Duplicates
Ignore duplicates don't error. Done in summarizeCollection

### 4. Negatives
Reject negatives. Ambigious -3--1 input output

### 5. Invalid tokens
Reject invalid tokens 'a'

### 6. Null / emtpy input
Empty result. Empty in equals empty out for both interface methods

### 7. Whitespace in input
Trim

### 8. Max integer
Catch n + 1 over flow error. where n Integer.MAX_VALUE

### 9. Empty tokens
Just skip

### 10. No ranges in input
1,3,5 -> 1, 3, 5

### 11. One range in input
3,4,5,6 - > 3-6

### 12. a range of two numbers
1,2 -> 1-2

### 13. Single number single output
4 - > 4

### 14. Whitespace only in input
Should give empty output "    " -> ""

