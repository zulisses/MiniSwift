package interpreter.type;

public class TypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TypeException() {
        super("Tipo inválido");
    }

}
