package test;

import static utilities.args.ArgsException.ErrorCode.*;

import junit.framework.TestCase;
import utilities.args.ArgsException;
public class ArgsExceptionTest extends TestCase {
    public void testUnexpectedMessage() throws Exception {
        ArgsException e = new ArgsException(UNEXPECTED_ARGUMENT, 'x', null);
        assertEquals("Argument -x unexpected.", e.errorMessage());
    }

    public void testMissingStringMessage() throws Exception {
        ArgsException e = new ArgsException(MISSING_STRING, 'x', null);
        assertEquals("Could not find string parameter for -x.", e.errorMessage());
    }

    public void testMissingIntegerMessage() throws Exception {
        ArgsException e = new ArgsException(MISSING_INTEGER, 'x', null);
        assertEquals("Could not find integer parameter for -x.", e.errorMessage());
    }

    public void testInvalidIntegerMessage() throws Exception {
        ArgsException e = new ArgsException(INVALID_INTEGER, 'x', "Forty two");
        assertEquals("Argument -x expects an integer but was 'Forty two'.", e.errorMessage());
    }

    public void testInvalidDoubleMessage() throws Exception {
        ArgsException e = new ArgsException(INVALID_DOUBLE, 'x', "Forty two");
        assertEquals("Argument -x expects a double but was 'Forty two'.", e.errorMessage());
    }

    public void testMissingDoubleMessage() throws Exception {
        ArgsException e = new ArgsException(MISSING_DOUBLE, 'x', null);
        assertEquals("Could not find double parameter for -x.", e.errorMessage());
    }
}
