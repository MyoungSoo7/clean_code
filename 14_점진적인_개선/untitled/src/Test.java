import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test {

    @org.junit.jupiter.api.Test
    public void testSimpleDoublePresent() throws Exception {
        Args args = new Args("x##", new String[] {"-x", "42.3"});
        assertTrue(args.isValid());
        assertEquals(1, args.cardinality());
        assertTrue(args.has('x'));
        assertEquals(42.3, args.getDouble('x'), .001);
    }

    @org.junit.jupiter.api.Test
    public void testInvalidDouble() throws Exception {
        Args args = new Args("x##", new String[] {"-x", "Forty two"});
        assertFalse(args.isValid());
        assertEquals(0, args.cardinality());
        assertFalse(args.has('x'));
        assertEquals(0, args.getInt('x'));
        assertEquals("Argument -x expects a double but was 'Forty two'.", args.errorMessage());
    }

    @org.junit.jupiter.api.Test
    public void testMissingDouble() throws Exception {
        Args args = new Args("x##", new String[]{"-x"});
        assertFalse(args.isValid());
        assertEquals(0, args.cardinality());
        assertFalse(args.has('x'));
        assertEquals(0.0, args.getDouble('x'), 0.01);
        assertEquals("Could not find double parameter for -x.", args.errorMessage());
    }

}
