package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WearableDeviceTest {
    private WearableDevice validWearable, invalidWearable;

    @After
    public void tearDown() {
        validWearable = invalidWearable = null;
    }

    @Before
    public void setUp() {
        Manufacturer manufacturer = new Manufacturer("Apple", 500);
        Manufacturer invalidManufacturer = new Manufacturer("ABCDEFGHIJKLMNOPQRSTU", 0);

        // 创建具体的WearableDevice子类对象，这里以SmartWatch为例
        validWearable = new SmartWatch("Apple Watch", 399.99, manufacturer, "123456", "Aluminum", "Medium", "AMOLED");
        invalidWearable = new SmartWatch("Apple Watch Ultra", 19.99, invalidManufacturer, "12345678910", "LongMaterialNameExceedsLimit", "ExtraLargeSize", "Unknown");
    }

    @Test
    public void shouldSetSizeWhenValid() {
        WearableDevice device = new SmartWatch("Apple Watch", 399.99, new Manufacturer("Apple", 500), "123456", "Aluminum", "Medium", "AMOLED");
        device.setSize("Small");
        assertEquals("Small", device.getSize());
    }

    @Test
    public void shouldNotSetSizeWhenInvalid() {
        WearableDevice device = new SmartWatch("Apple Watch", 399.99, new Manufacturer("Apple", 500), "123456", "Aluminum", "Medium", "AMOLED");
        device.setSize("SizeTooBigToFit");
        assertEquals("Medium", device.getSize());
    }

    @Test
    public void shouldSetMaterialWhenValid() {
        WearableDevice device = new SmartWatch("Apple Watch", 399.99, new Manufacturer("Apple", 500), "123456", "Aluminum", "Medium", "AMOLED");
        device.setMaterial("Titanium");
        assertEquals("Titanium", device.getMaterial());
    }

    @Test
    public void shouldNotSetMaterialWhenInvalid() {
        WearableDevice device = new SmartWatch("Apple Watch", 399.99, new Manufacturer("Apple", 500), "123456", "Aluminum", "Medium", "AMOLED");
        device.setMaterial("MaterialNameThatIsTooLong");
        assertEquals("Aluminum", device.getMaterial());
    }

    @Test
    public void shouldReturnCorrectToString() {
        WearableDevice device = new SmartWatch("Apple Watch", 399.99, new Manufacturer("Apple", 500), "123456", "Aluminum", "Medium", "AMOLED");
        String expected = "Material: Aluminum, Size: Medium";
        assertTrue(device.toString().contains(expected));
    }
}
