import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test {

    @org.junit.jupiter.api.Test
    public void setSimpleDoublePresent() throws Exception {
        Args args = new Args("x##", new String[] {"-x", "42.3"});
        assertTrue(args.isValid());
        assertEquals(1, args.cardinality());
        assertTrue(args.has('x'));
        assertEquals(42.3, args.getDouble('x'), .001);
    }
}
