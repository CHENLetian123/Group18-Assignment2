package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WearableDeviceTest {
    private WearableDevice validWearable1, validWearable2, invalidWearable1 ,invalidWearable2;

    @After
    public void tearDown() {
        validWearable1 = validWearable2  = invalidWearable1 = invalidWearable2 = null;
    }

    @Before
    public void setUp() {
        Manufacturer manufacturer = new Manufacturer("Apple", 1020);

        // 创建WearableDevice的子类对象
        validWearable1 = new SmartWatch("Apple Watch", 399.99, manufacturer, "123456", "Aluminum", "Medium", "AMOLED");
        validWearable2 = new SmartBand("Apple Band", 99.99, manufacturer, "123456", "Plastic", "Small", true);
        invalidWearable1 = new SmartWatch("Apple Watch Super", 19.99, manufacturer, "12345678910", "LooooooooooooooongMaterialName", "ExtraLargeSize", "Unknown");
        invalidWearable2 = new SmartBand("Apple Band Super", 199.99, manufacturer, "12345678910", "LooooooooooooooooooongMaterialName", "ExtraLargeSize", true);
    }


    @Test
    public void testGetMaterial() {
        assertEquals("Aluminum", validWearable1.getMaterial());
        assertEquals("Plastic", validWearable2.getMaterial());
        assertEquals(null, invalidWearable1.getMaterial());
        assertEquals(null, invalidWearable2.getMaterial());
    }
    @Test
    public void testSetSize() {
        assertEquals("Medium", validWearable1.getSize());
        assertEquals("Small", validWearable2.getSize());
        validWearable1.setSize("Small");
        assertEquals("Small", validWearable1.getSize());
        validWearable2.setSize("Large");
        assertEquals("Large", validWearable2.getSize());
        validWearable1.setSize("Laaaaaaaaaaaaaaaarge");
        assertEquals("Small", validWearable1.getSize());
        validWearable2.setSize("Laaaaaaaaaaaaaaaaaarge");
        assertEquals("Large", validWearable2.getSize());
    }


    @Test
    public void testSetMaterial() {
        assertEquals("Aluminum", validWearable1.getMaterial());
        assertEquals("Plastic", validWearable2.getMaterial());
        validWearable1.setMaterial("Plastic");
        assertEquals("Plastic", validWearable1.getMaterial());
        validWearable2.setMaterial("Aluminum");
        assertEquals("Aluminum", validWearable2.getMaterial());
        validWearable1.setMaterial("LoooooooooooongMaterial");
        assertEquals("Plastic", validWearable1.getMaterial());
        validWearable2.setMaterial("LoooooooooooooooooongMaterial");
        assertEquals("Aluminum", validWearable2.getMaterial());
    }



    @Test
    public void testToString() {
        System.out.println("WearableDeviceTest");
        System.out.println(validWearable1.toString());
        System.out.println(validWearable2.toString());
        System.out.println(invalidWearable1.toString());
        System.out.println(invalidWearable2.toString());
        String expected1 = "Material: Aluminum, Size: Medium";
        assertTrue(validWearable1.toString().contains(expected1));
        String expected2 = "Material: Plastic, Size: Small";
        assertTrue(validWearable2.toString().contains(expected2));
        String expected3 = "Material: null, Size: null";
        assertTrue(invalidWearable1.toString().contains(expected3));
        String expected4 = "Material: null, Size: null";
        assertTrue(invalidWearable2.toString().contains(expected4));
    }
}
