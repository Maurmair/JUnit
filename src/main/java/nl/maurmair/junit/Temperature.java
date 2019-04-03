package nl.maurmair.junit;

import java.util.Objects;

public class Temperature {
    private static final float FREEZING_TEMPERATURE = 0F;
    private static final float BOILING_TEMPERATURE = 100F;
    private float value;

    public Temperature(float value) {
        setValue(value);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        if (value <= -273.15f) {
            throw new InvalidTemperatureException();
        }
        this.value = value;
    }

    public boolean isBoiling() {
        return value >= BOILING_TEMPERATURE;
    }

    public boolean isFreezing() {
        return value <= FREEZING_TEMPERATURE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Temperature)) return false;
        Temperature that = (Temperature) o;
        return Float.compare(that.getValue(), getValue()) == 0 &&
                isBoiling() == that.isBoiling() &&
                isFreezing() == that.isFreezing();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), isBoiling(), isFreezing());
    }
}
