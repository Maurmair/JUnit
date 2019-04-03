package nl.maurmair.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TemperatureTest {

    private Temperature t;

    @BeforeEach
    public void init(){
        t = new Temperature(0);
    }

    @Test
    @DisplayName("Aanmaken Temperatuur Object met initiele waarde")
    public void testConstructor() {
        Temperature temperature = new Temperature(5f);
        assertEquals(5f,temperature.getValue());
    }

    @Test
    @DisplayName("Veranderen van temeperatuur")
    public void testValue() {
        Temperature t = new Temperature(10f);
        assertEquals(10f, t.getValue());
        t.setValue(40f);
        assertEquals(40f,t.getValue());
    }

    @Test
    @DisplayName("Testen van de EqualsMethode")
    public void testEquals(){
        Temperature t1 = new Temperature(5f);
        Temperature t2 = new Temperature(5f);
        assertEquals(t1,t2);
    }

    @Test
    @DisplayName("Testen van de hash methode")
    public void testHash(){
        Temperature t1 = new Temperature(10f);
        Temperature t2 = new Temperature(10f);
        assertEquals(t1,t2);
        assertEquals(t1.hashCode(),t2.hashCode());
    }

    @Test
    @DisplayName("Water kookt")
    public void testIsBoiling(){
        t.setValue(100);
        assertTrue(t.isBoiling());
        t.setValue(100.1F);
        assertTrue(t.isBoiling());
    }

    @Test
    @DisplayName("Water is bevroren")
    public void testIsFreezing(){
        t.setValue(10);
        assertFalse(t.isFreezing());

        t.setValue(-0.01f);
        assertTrue(t.isFreezing());
    }

    @Test
    @DisplayName("Testen van Exceptie")
    public void testException(){
        assertThrows(InvalidTemperatureException.class, () -> t.setValue(-400));
        assertThrows(InvalidTemperatureException.class, () -> t.setValue(-273.16F));
        assertThrows(InvalidTemperatureException.class, () -> t.setValue(-273.15F));
        assertThrows(InvalidTemperatureException.class, () -> new Temperature(-273.15F));
        assertDoesNotThrow(() -> t.setValue(-273.14F));
    }

}
