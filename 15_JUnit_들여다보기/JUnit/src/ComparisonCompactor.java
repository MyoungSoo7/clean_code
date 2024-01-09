public class ComparisonCompactor {
    private int contextLength;
    private String expected;
    private String actual;
    private int prefix;
    private int suffix;

    public ComparisonCompactor(int contextLength, String expected, String actual) {
        this.contextLength = contextLength;
        this.expected = expected;
        this.actual = actual;
    }

    public String compact(String msg) {
        if (expected == null || actual == null || expected.equals(actual))
            return Assert.format(msg, expected, actual);

        prefix = 0;
        for (; prefix < Math.min(expected.length(), actual.length()); prefix++) {
            if (expected.charAt(prefix) != actual.charAt(prefix))
                break;
        }

        int sfx1 = expected.length() - 1;
        int sfx2 = actual.length() -1;
        for (; sfx2 >= prefix && sfx1 >= prefix; sfx2--, sfx1--) {
            if (expected.charAt(sfx1) != actual.charAt(sfx2))
                break;
        }

        suffix = expected.length() - sfx1;
        String cmp1 = compactString(expected);
        String cmp2 = compactString(actual);
        return Assert.format(msg, cmp1, cmp2);
    }

    private String compactString(String s) {
        String result = "[" + s.substring(prefix, s.length() - suffix + 1) + "]";
        if (prefix > 0)
            result = (prefix > contextLength ? "..." : "") + expected.substring(Math.max(0, prefix - contextLength),
                    prefix) + result;
        if (suffix > 0) {
            int end = Math.min(expected.length() - suffix + 1 + contextLength, expected.length());
            result = result + (expected.substring(expected.length() - suffix + 1, end) + (
                    expected.length() - suffix + 1 < expected.length() - contextLength
                    ? "..." : ""));
        }
        return result;
    }


}