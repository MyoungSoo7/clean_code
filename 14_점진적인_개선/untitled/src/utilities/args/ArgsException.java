package utilities.args;

public class ArgsException extends Exception {
    private char errorArgumentId = '\0';
    private String errorParameter = "TILT";
    private ErrorCode errorCode = ErrorCode.OK;

    private ArgsException() {}

    public ArgsException(String message) {super(message);}

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ArgsException(ErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
    }

    public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
        this.errorParameter = errorParameter;
    }

    public char getErrorArgumentId() {
        return errorArgumentId;
    }

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }

    public String getErrorParameter() {
        return errorParameter;
    }

    public void setErrorParameter(String errorParameter) {
        this.errorParameter = errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String errorMessage() throws Exception {
        return switch (errorCode) {
            case OK -> throw new Exception("TILT: Should not get here.");
            case UNEXPECTED_ARGUMENT -> String.format("Argument -%c unexpected.", errorArgumentId);
            case INVALID_FORMAT -> String.format("'%s' is not a valid argument format.", errorParameter);
            case INVALID_ARGUMENT_NAME -> String.format("'%c' is not a valid argument name.", errorArgumentId);
            case MISSING_STRING -> String.format("Could not find string parameter for -%c.", errorArgumentId);
            case INVALID_INTEGER ->
                    String.format("Argument -%c expects an integer but was '%s.", errorArgumentId, errorParameter);
            case MISSING_INTEGER -> String.format("Could not find integer parameter for -%c.", errorArgumentId);
            case INVALID_DOUBLE ->
                    String.format("Argument -%c expects a double but was '%s'.", errorArgumentId, errorParameter);
            case MISSING_DOUBLE -> String.format("Could not find double parameter for -%c.", errorArgumentId);
        };
    }

    public enum ErrorCode {
        OK, INVALID_FORMAT, INVALID_ARGUMENT_NAME, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, UNEXPECTED_ARGUMENT, MISSING_DOUBLE, INVALID_DOUBLE
    }



}