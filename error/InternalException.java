package error;

public class InternalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InternalException(String msg) {
        super(msg);
    }

}
