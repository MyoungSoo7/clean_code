public class MyComparisonCompactor {
    private final String DELTA_START = "[";
    private final String DELTA_END = "]";
    private final int contextLength;
    private final String expected;
    private final String actual;
    private String compactExpected;
    private String compactActual;
    private int prefixLength;
    private int suffixLength;

    public MyComparisonCompactor(int contextLength, String expected, String actual) {
        this.contextLength = contextLength;
        this.expected = expected;
        this.actual = actual;
    }

    public String formatCompactedComparison(String msg) {
        if (canBeCompacted()) {
            compactedComparison();
            return Assert.format(msg, compactExpected, compactActual);
        } else {
            return Assert.format(msg, expected, actual);
        }
    }

    private boolean canBeCompacted() {
        return expected != null && actual != null && !areStringEqual();
    }

    private boolean areStringEqual() {
        return expected.equals(actual);
    }

    private void findCommonPrefixAndSuffix() {
        findCommonPrefix();
        suffixLength = 0;
        for (; !suffixOverlapsPrefix(suffixLength); suffixLength++) {
            if (charFromEnd(expected, suffixLength) != charFromEnd(actual, suffixLength)) {
                break;
            }
        }
    }

    private char charFromEnd(String s, int i) {
        return s.charAt(s.length() - i - 1);
    }

    private boolean suffixOverlapsPrefix(int suffixLength) {
        return actual.length() - suffixLength <= prefixLength || expected.length() - suffixLength <= prefixLength;
    }

    private void findCommonPrefix() {
        prefixLength = 0;
        int end = Math.min(expected.length(), actual.length());
        for (; prefixLength < end; prefixLength++) {
            if (expected.charAt(prefixLength) != actual.charAt(prefixLength)) {
                break;
            }
        }
    }

    private String compactString(String source) {
        return computeCommonPrefix() + DELTA_START + source.substring(prefixLength, source.length() - suffixLength) + DELTA_END + computeCommonSuffix();

    }

    private String computeCommonPrefix() {
        return (prefixLength > contextLength ? "..." : "") + expected.substring(Math.max(0, prefixLength - contextLength),
                prefixLength);
    }

    private String computeCommonSuffix() {
        int end = Math.min(expected.length() - suffixLength + contextLength, expected.length());
        return (expected.substring(expected.length() - suffixLength, end) + (
                expected.length() - suffixLength < expected.length() - contextLength
                        ? "..." : ""));
    }

    private void compactedComparison() {
        findCommonPrefixAndSuffix();
        compactExpected = compactString(expected);
        compactActual = compactString(actual);
    }

}