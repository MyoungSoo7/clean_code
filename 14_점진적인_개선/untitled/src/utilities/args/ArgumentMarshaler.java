package utilities.args;

import java.util.Iterator;
import utilities.args.ArgsException;

public interface ArgumentMarshaler {
    void set(Iterator<String> currentArgument) throws ArgsException;
}


