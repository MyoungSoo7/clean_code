import ArgsException.ErrorCode;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface ArgumentMarshaler {
    void set(Iterator<String> currentArgument) throws ArgsException;
    Object get();
}


