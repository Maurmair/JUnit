package nl.maurmair.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

public class ThermostatMockTest {

    private final static int INTERVAL = 10;
    private Thermostat thermostat;
    private HeatingMock heatingMock;
    private SensorMock sensorMock;

    private class HeatingMock implements Heating {
        private boolean status;
        public void setHeating(boolean status){
            this.status = status;
        }
        public boolean isHeating(){
            return status;
        }
    }

    private class SensorMock implements Sensor {
        private float temp;
        private boolean called;
        public Temperature getTemperature() {
            called = true;
            return new Temperature(temp);
        }
        public void setTemp(float temp){
            this.temp = temp;
        }
        public boolean isCalled() {
            return called;
        }
        public void setCalled(boolean status) {
            called = status;
        }
    }

    @BeforeEach
    public void init(){
        sensorMock = new SensorMock();
        heatingMock = new HeatingMock();
        thermostat = new Thermostat(heatingMock, sensorMock);
        thermostat.setInterval(INTERVAL);
        thermostat.start();
    }

    @AfterEach
    public void destroy() {
        thermostat.stop();
    }

    @Test
    public void testThermostat() throws InterruptedException {
        thermostat.setTargetTemperature(new Temperature(20));
        sensorMock.setTemp(15);
        sensorMock.setCalled(false);
        heatingMock.setHeating(false);
        Thread.sleep(INTERVAL * 3);
        assertTrue(thermostat.isHeating());
        assertTrue(sensorMock.isCalled());
        assertTrue(heatingMock.isHeating());
    }

    @Test
    public void testChangeCurrent() throws InterruptedException{
        thermostat.setTargetTemperature(new Temperature(20f));
        sensorMock.setTemp(15f);
        sensorMock.setCalled(false);
        heatingMock.setHeating(false);
        Thread.sleep(INTERVAL*3);
        assertTrue(thermostat.isHeating());
        assertTrue(sensorMock.isCalled());
        assertTrue(heatingMock.isHeating());
        sensorMock.setTemp(22.1f);
        Thread.sleep(INTERVAL*3);
        assertFalse(thermostat.isHeating());
        assertTrue(sensorMock.isCalled());
        assertFalse(heatingMock.isHeating());
    }

    @Test
    public void testChangeTarget() throws InterruptedException{
        thermostat.setTargetTemperature(new Temperature(20f));
        sensorMock.setTemp(15f);
        sensorMock.setCalled(false);
        heatingMock.setHeating(false);
        Thread.sleep(INTERVAL*3);
        assertTrue(thermostat.isHeating());
        assertTrue(sensorMock.isCalled());
        assertTrue(heatingMock.isHeating());
        thermostat.setTargetTemperature(new Temperature(14.9f));
        sensorMock.setCalled(false);
        Thread.sleep(INTERVAL*3);
        assertFalse(thermostat.isHeating());
        assertTrue(sensorMock.isCalled());
        assertFalse(heatingMock.isHeating());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void paramTest(float target, float current, boolean status) throws InterruptedException {
        thermostat.setTargetTemperature(new Temperature(target));
        sensorMock.setTemp(current);
        Thread.sleep(INTERVAL*3);
        assertEquals(status, thermostat.isHeating());
    }
}
