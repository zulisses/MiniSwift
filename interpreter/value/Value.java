package interpreter.value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import error.InternalException;
import interpreter.type.Type;

public class Value {

    private static final class ArrayData extends ArrayList<Object> {
        private static final long serialVersionUID = 1L;

        private ArrayData(List<?> values) {
            super(values);
        }
    }

    private static final class DictData extends HashMap<Object, Object> {
        private static final long serialVersionUID = 1L;

        private DictData(Map<?, ?> values) {
            super(values);
        }
    }
    
    public final Type type;
    public final Object data;

    public Value(Type type, Object data) {
        Object checkedData;

        switch (type.getCategory()) {
            case Bool:
                checkedData = require(data, Boolean.class);
                break;
            case Int:
                checkedData = require(data, Integer.class);
                break;
            case Float:
                checkedData = require(data, Float.class);
                break;
            case Char:
                checkedData = require(data, Character.class);
                break;
            case String:
                checkedData = require(data, String.class);
                break;
            case Array:
                if (data instanceof ArrayData) {
                    checkedData = data;
                } else if (data instanceof List<?>) {
                    checkedData = new ArrayData((List<?>) data);
                } else {
                    throw invalidData(type);
                }
                break;
            case Dict:
                if (data instanceof DictData) {
                    checkedData = data;
                } else if (data instanceof Map<?, ?>) {
                    checkedData = new DictData((Map<?, ?>) data);
                } else {
                    throw invalidData(type);
                }
                break;
            default:
                throw new InternalException("Unreachable");
        }

        this.type = type;
        this.data = checkedData;
    }

    public List<Object> asArray() {
        if (!(data instanceof ArrayData))
            throw invalidData(type);

        return (ArrayData) data;
    }

    public Map<Object, Object> asDict() {
        if (!(data instanceof DictData))
            throw invalidData(type);

        return (DictData) data;
    }

    private static Object require(Object data, Class<?> expectedClass) {
        if (!expectedClass.isInstance(data))
            throw new InternalException("Invalid runtime value; expected " + expectedClass.getSimpleName());

        return data;
    }

    private static InternalException invalidData(Type type) {
        return new InternalException("Invalid runtime value for type " + type);
    }

    @Override
    public String toString() {
        return new StringBuffer()
            .append(type)
            .append("(")
            .append(data)
            .append(")")
            .toString();
    }

}
