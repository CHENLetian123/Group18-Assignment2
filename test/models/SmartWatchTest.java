package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartWatchTest {
    private SmartWatch validSmartWatch1;
    private SmartWatch validSmartWatch2;
    private SmartWatch validSmartWatch3;
    private SmartWatch invalidSmartWatch;

    @BeforeEach
    void setUp() {
        Manufacturer manufacturer = new Manufacturer("Apple", 1020);
        validSmartWatch1 = new SmartWatch("Apple Watch Series 8", 399.99, manufacturer, "AW1234", "Aluminium", "M", "AMOLED");
        validSmartWatch2 = new SmartWatch("Apple Watch Series 9", 599.99, manufacturer, "AW1235", "Aluminium", "M", "TFT");
        validSmartWatch3 = new SmartWatch("Apple Watch Ultra", 799.99, manufacturer, "AW1236", "stainless steel", "M", "LCD");
        invalidSmartWatch = new SmartWatch("Cheap Watch", 49.99, manufacturer, "CW5678", "Plastic", "L", "ROG Swift OLED");
    }

    @AfterEach
    void tearDown() {
        validSmartWatch1 = invalidSmartWatch = null;
    }

    @Test
    public void testGetDisplayType() {
        assertEquals("AMOLED", validSmartWatch1.getDisplayType());
        assertEquals("TCD", invalidSmartWatch.getDisplayType()); // Invalid display type should default to "TCD"
    }

    @Test
    public void testSetValidDisplayType() {
        assertEquals("AMOLED", validSmartWatch1.getDisplayType());
        validSmartWatch1.setDisplayType("LCD");
        assertEquals("LCD", validSmartWatch1.getDisplayType());
        validSmartWatch1.setDisplayType("LED");
        assertEquals("LED", validSmartWatch1.getDisplayType());
        validSmartWatch1.setDisplayType("TFT");
        assertEquals("TFT", validSmartWatch1.getDisplayType());
    }

    @Test
    public void testSetInvalidDisplayType() {
        assertEquals("TCD", invalidSmartWatch.getDisplayType());
        validSmartWatch1.setDisplayType("OLED");
        assertEquals("TCD", validSmartWatch1.getDisplayType());
        validSmartWatch1.setDisplayType("ROG Swift OLED");
        assertEquals("TCD", validSmartWatch1.getDisplayType());
    }

    @Test
    public void testConnectToInternet() {
        assertEquals("Connects to the Internet via Bluetooth!", validSmartWatch1.connectToInternet());
        assertEquals("Connects to the Internet via Bluetooth!", validSmartWatch2.connectToInternet());
        assertEquals("Connects to the Internet via Bluetooth!", validSmartWatch3.connectToInternet());
        assertEquals("Connects to the Internet via Bluetooth!", invalidSmartWatch.connectToInternet());
    }

    @Test
    public void testGetInsurancePremium() {
        assertEquals(23.99, validSmartWatch1.getInsurancePremium(), 0.01); // 399.99 * 0.06 = 23.99
    }

    @Test
    public void testToString() {
        System.out.println("SmartWatchTest");
        System.out.println(validSmartWatch1.toString());
        System.out.println(validSmartWatch2.toString());
        System.out.println(validSmartWatch3.toString());
        System.out.println(invalidSmartWatch.toString());

        String expectedValid1 = "Display: AMOLED, The Internet condition is Connects to the Internet via Bluetooth!, Insurance Premium: €23.99";
        assertTrue(validSmartWatch1.toString().contains(expectedValid1));
        String expectedValid2 = "Display: TFT, The Internet condition is Connects to the Internet via Bluetooth!, Insurance Premium: €35.99";
        assertTrue(validSmartWatch2.toString().contains(expectedValid2));
        String expectedValid3 = "Display: LCD, The Internet condition is Connects to the Internet via Bluetooth!, Insurance Premium: €47.99";
        assertTrue(validSmartWatch3.toString().contains(expectedValid3));
        String expectedInvalid = "Display: TCD, The Internet condition is Connects to the Internet via Bluetooth!, Insurance Premium: €2.99";
        assertTrue(invalidSmartWatch.toString().contains(expectedInvalid));
    }

}