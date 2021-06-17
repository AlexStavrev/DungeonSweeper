package model.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * This enum shows the values a {@link model.Cell} can have
 *
 * @author WrexBG
 */
public enum Value {
    //The enums
    ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
    BOMB(-1);


    // The value
    int value;
    // A map of all enums. Used to get an Enum from its int {@link Value#value}
    private static final Map<Integer, Value> intToValueMap = new HashMap<>();

    /**
     * Standard constructor accepting value
     * @param value The integer value for this enum; [0-8] or -1 for a bomb
     */
    Value(int value) {
        setValue(value);
    }

    /*
     * A static anonymous method to put all enums to a map {@link Value#intToValueMap}
     */
    static {
        for (Value type : Value.values()) {
            intToValueMap.put(type.value, type);
        }
    }

    /**
     * A static method to get an enum from an int value
     * @param value The integer value for this enum; [0-8] or -1 for a bomb
     * @return the enum with that value
     */
    public static Value fromInt(int value) {
        Value type = intToValueMap.get(value);
        return (type == null) ? Value.ZERO : type;
    }

    /**
     * A setter method for the value used in its {@link #Value(int) constructor}
     * @param value The integer value for this enum; [0-8] or -1 for a bomb
     */
    private void setValue(int value) {
        this.value = value;
    }
    /**
     * A getter method for the value
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * A method to check if the value is a bomb
     * @return boolean True if the value is -1 (bomb)
     */
    public boolean isABomb() {
        return (value == Value.BOMB.getValue());
    }

    /**
     * A method to check if the value is max
     * @return boolean True if the value is max possible value (8 in a standard grid)
     */
    public boolean isMaxValue() {
        Value max = Arrays.stream(values()).max(Comparator.comparing(Value::getValue)).get();
        return (value == max.getValue());
    }
}
