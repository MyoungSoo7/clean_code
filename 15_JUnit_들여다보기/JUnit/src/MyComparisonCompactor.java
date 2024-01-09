public class MyComparisonCompactor {
    private final int contextLength;
    private final String expected;
    private final String actual;
    private String compactExpected;
    private String compactActual;
    private int prefixLength;
    private int suffixIndex;

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

    private String compactString(String s) {
        String result = "[" + s.substring(prefixLength, s.length() - suffixIndex + 1) + "]";
        if (prefixLength > 0) {
            result = (prefixLength > contextLength ? "..." : "") + expected.substring(Math.max(0, prefixLength - contextLength),
                    prefixLength) + result;
        }
        if (suffixIndex > 0) {
            int end = Math.min(expected.length() - suffixIndex + 1 + contextLength, expected.length());
            result = result + (expected.substring(expected.length() - suffixIndex + 1, end) + (
                    expected.length() - suffixIndex + 1 < expected.length() - contextLength
                            ? "..." : ""));
        }
        return result;
    }

    private void compactedComparison() {
        findCommonPrefixAndSuffix();
        compactExpected = compactString(expected);
        compactActual = compactString(actual);
    }

    private void findCommonPrefixAndSuffix() {
        findCommonPrefix();
        int suffixLength = 1;
        for (; !suffixOverlapsPrefix(suffixLength); suffixLength++) {
            if (charFromEnd(expected, suffixLength) != charFromEnd(actual, suffixLength)) {
                break;
            }
        }
        suffixIndex = suffixLength;
    }

    private char charFromEnd(String s, int i) {
        return s.charAt(s.length() - i);
    }

    private boolean suffixOverlapsPrefix(int suffixLength) {
        return actual.length() - suffixLength < prefixLength || expected.length() - suffixLength < prefixLength;
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

}