package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartBandTest {
    private SmartBand validSmartBand, invalidSmartBand;

    @BeforeEach
    void setUp() {
        Manufacturer manufacturer = new Manufacturer("Samsung", 333);
        validSmartBand = new SmartBand("Samsung Smart Band 1", 100, manufacturer, "123456", "Plastic", "Small", false);
        invalidSmartBand = new SmartBand("Samsung Smart Band 2", 200, manufacturer, "12345678", "Metal", "Large", true);
    }

    @AfterEach
    void tearDown() {
        validSmartBand = invalidSmartBand = null;
    }

    @Test
    void isHeartRateMonitor() {
        assertFalse(validSmartBand.isHeartRateMonitor());
        assertTrue(invalidSmartBand.isHeartRateMonitor());
    }

    @Test
    void setHeartRateMonitor() {
        assertFalse(validSmartBand.isHeartRateMonitor());
        validSmartBand.setHeartRateMonitor(true);
        assertTrue(validSmartBand.isHeartRateMonitor());
        assertTrue(invalidSmartBand.isHeartRateMonitor());
        invalidSmartBand.setHeartRateMonitor(false);
        assertFalse(invalidSmartBand.isHeartRateMonitor());
    }

    @Test
    void getInsurancePremium() {
    }

    @Test
    void connectToInternet() {
        assertEquals("Connects to the internet via Companion App", validSmartBand.connectToInternet());
        assertEquals("Connects to the internet via Companion App", invalidSmartBand.connectToInternet());
    }

    @Test
    void testToString() {
        System.out.println("SmartBandTest");
        System.out.println(validSmartBand.toString());
        System.out.println(invalidSmartBand.toString());

        String expectedValid = "No Heart Rate Monitor included, Insurance Premium: 7.0, Internet Connection: Connects to the internet via Companion App";
        assertTrue(validSmartBand.toString().contains(expectedValid));
        String expectedInvalid = "Includes Heart Rate Monitor, Insurance Premium: 14.0, Internet Connection: Connects to the internet via Companion App";
        assertTrue(invalidSmartBand.toString().contains(expectedInvalid));
    }
}