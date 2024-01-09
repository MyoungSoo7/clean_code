public class MyComparisonCompactor {
    private final int contextLength;
    private final String expected;
    private final String actual;
    private String compactExpected;
    private String compactActual;
    private int prefixIndex;
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
        String result = "[" + s.substring(prefixIndex, s.length() - suffixIndex + 1) + "]";
        if (prefixIndex > 0) {
            result = (prefixIndex > contextLength ? "..." : "") + expected.substring(Math.max(0, prefixIndex - contextLength),
                    prefixIndex) + result;
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

    private void findCommonPrefix() {
        prefixIndex = 0;
        int end = Math.min(expected.length(), actual.length());
        for (; prefixIndex < end; prefixIndex++) {
            if (expected.charAt(prefixIndex) != actual.charAt(prefixIndex)) {
                break;
            }
        }
    }

    private void findCommonPrefixAndSuffix() {
        findCommonPrefix();
        int sfx1 = expected.length() - 1;
        int sfx2 = actual.length() - 1;
        for (; sfx2 >= prefixIndex && sfx1 >= prefixIndex; sfx2--, sfx1--) {
            if (expected.charAt(sfx1) != actual.charAt(sfx2)) {
                break;
            }
        }
        suffixIndex =  expected.length() - sfx1;
    }
}