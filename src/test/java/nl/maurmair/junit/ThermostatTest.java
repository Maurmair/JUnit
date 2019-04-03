package nl.maurmair.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThermostatTest {

    private final static int INTERVAL = 10;
    private Thermostat thermostat;
    private HeatingStub heatingStub;
    private SensorStub sensorStub;

    private class HeatingStub implements Heating {
        public void setHeating(boolean status) {

        }
    }

    private class SensorStub implements Sensor {
        private float temp;

        public Temperature getTemperature() {
            return new Temperature(temp);
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }
    }

    @BeforeEach
    public void init(){
        sensorStub = new SensorStub();
        heatingStub = new HeatingStub();
        thermostat = new Thermostat(heatingStub, sensorStub);
        thermostat.setInterval(INTERVAL);
        thermostat.start();
    }

    @AfterEach
    public void destroy(){
        thermostat.stop();
    }

    // Test methods
    @Test
    public void testThermostat() throws InterruptedException {
        thermostat.setTargetTemperature(new Temperature(20F));
        sensorStub.setTemp(15F);
        Thread.sleep(INTERVAL*3);
        assertTrue(thermostat.isHeating());
    }

    @Test
    public void testChangeCurrent() throws InterruptedException {
        thermostat.setTargetTemperature(new Temperature(30F));
        sensorStub.setTemp(28F);
        Thread.sleep(INTERVAL);
        assertTrue(thermostat.isHeating());
        sensorStub.setTemp(30.1F);
        Thread.sleep(INTERVAL*4);
        assertFalse(thermostat.isHeating());
    }

    @Test
    public void testChangeTarget() throws InterruptedException {
        thermostat.setTargetTemperature(new Temperature(30F));
        sensorStub.setTemp(20F);
        Thread.sleep(INTERVAL);
        assertTrue(thermostat.isHeating());
        thermostat.setTargetTemperature(new Temperature(19F));
        Thread.sleep(INTERVAL*5);
        assertFalse(thermostat.isHeating());
    }


}
