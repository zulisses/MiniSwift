package syntatic;

public class SyntaticException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SyntaticException(int line, String reason) {
       super(String.format("%02d: %s", line, reason));
    }

}
