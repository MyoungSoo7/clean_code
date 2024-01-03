public class ArgsException extends Exception {
    private char errorArgumentId = "\0";
    private String errorParameter = "TILT";
    private ErrorCode errorcode = ErrorCode.OK;

    private ArgsException() {}

    public ArgsException(String message) {super(message);}

    public enum ErrorCode {
        OK, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, UNEXPECTED_ARGUMENT, MISSING_DOUBLE, INVALID_DOUBLE
    }

}