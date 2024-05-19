package controllers;

import models.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TechnologyDeviceAPITest {
    private Manufacturer apple = new Manufacturer("Apple", 1020);
    private Manufacturer samsung = new Manufacturer("Samsung", 1200);
    private Manufacturer hitachi = new Manufacturer("Hitachi", 1325);
    private Manufacturer tesla = new Manufacturer("Tesla", 3245);

    private TechnologyDeviceAPI populatedDevices = new TechnologyDeviceAPI(new File("technologyDevicesTest.xml"));
    private TechnologyDeviceAPI emptyDevices = new TechnologyDeviceAPI(new File("technologyDevicesemptyTest.xml"));

    @BeforeEach
    void setUp() {
        try {
            populatedDevices.load();
            emptyDevices.load();
        } catch (Exception e){
            System.out.println(e);
        }

    }

    @AfterEach
    void tearDown() {
    }
    @Nested
    class GettersAndSetters {

        @Test
        void testGetAllTechnologies() {
            emptyDevices.addTechnologyDevice(new SmartWatch("Apple Watch Series 5", 299.99, apple, "W125", "OLED", "Metal", "44mm"));
            List<Technology> allTech = emptyDevices.getAllTechnologies();
            assertEquals(1, allTech.size());
            assertEquals("Apple Watch Series 5", allTech.get(0).getModelName());
        }

        @Test
        void testRetrieveTechnologyIndexValidId() {
            emptyDevices.addTechnologyDevice(new Tablet("iPad Pro", 999.99, apple, "T126", "A12Z", 256, "iOS"));
            int index = emptyDevices.retrieveTechnologyIndex("T126");
            assertEquals(0, index);
        }

        @Test
        void testRetrieveTechnologyIndexInvalidId() {
            int index = populatedDevices.retrieveTechnologyIndex("NonExistentID");
            assertEquals(-1, index);
        }

        @Test
        void testGetManufacturerNameByExistingModelName() {
            populatedDevices.addTechnologyDevice(new Tablet("iPad Pro", 999.99, apple, "T131", "A12Z", 256, "iOS"));
            String manufacturerName = populatedDevices.getManufacturerNameByModelName("iPad Pro");
            assertEquals("Apple", manufacturerName);
        }

        @Test
        void testGetManufacturerNameByNonExistingModelName() {
            String manufacturerName = populatedDevices.getManufacturerNameByModelName("Nonexistent Model");
            assertEquals("No manufacturer found for this model name", manufacturerName);
        }

    }

    @Nested
    class CRUDMethods {
        @Test
        void addNewTechnologyDevicetoEmpty() {
            assertEquals(0, emptyDevices.numberOfTechnologyDevices());
            Tablet newTab = new Tablet("Galaxy Tab S7", 799.99, tesla, "123456", "Snapdragon 865", 64, "Android");
            emptyDevices.addTablet(newTab);
            assertEquals(1, emptyDevices.numberOfTechnologyDevices());
            Tablet newTab2 = new Tablet("Galaxy Tab S8", 799.99, samsung, "123457", "Snapdragon 865", 64, "Android");
            emptyDevices.addTablet(newTab2);
            assertEquals(2, emptyDevices.numberOfTechnologyDevices());
            SmartWatch newWatch = new SmartWatch("Galaxy Watch 3", 399.99, samsung, "123458", "AMOLED", "S", "LED");
            emptyDevices.addSmartWatch(newWatch);
            assertEquals(3, emptyDevices.numberOfTechnologyDevices());
            SmartBand newBand = new SmartBand("Fitbit Charge 4", 149.99, hitachi, "123459", "Plastic", "M", true);
            emptyDevices.addSmartBand(newBand);
            assertEquals(4, emptyDevices.numberOfTechnologyDevices());

        }
        @Test
        void addNewTechnologySameId() {
            assertEquals(0, emptyDevices.numberOfTechnologyDevices());
            Tablet newTab = new Tablet("Galaxy Tab S7", 799.99, tesla, "123456", "Snapdragon 865", 64, "Android");
            emptyDevices.addTechnologyDevice(newTab);
            assertEquals(1, emptyDevices.numberOfTechnologyDevices());
            Tablet newTab2 = new Tablet("Galaxy Tab S8", 799.99, samsung, "123456", "Snapdragon 865", 64, "Android");
            emptyDevices.addTechnologyDevice(newTab2);
            assertEquals(1, emptyDevices.numberOfTechnologyDevices());

        }

        @Test
        void updateExistingTablet() {
            Tablet originalTablet = new Tablet("iPad Mini", 399.99, apple, "A124", "A12", 64, "iOS");
            populatedDevices.addTechnologyDevice(originalTablet);
            Tablet updatedTablet = new Tablet("iPad Mini 2", 499.99, apple, "A124", "A12X", 128, "iOS");
            boolean updated = populatedDevices.updateTablet("A124", updatedTablet);
            assertTrue(updated);
            Technology retrieved = populatedDevices.getTechnologyByModelName("iPad Mini 2");
            assertEquals(499.99, retrieved.getPrice());
            assertEquals(128, ((Tablet)retrieved).getStorage());
        }

        @Test
            void updateExistingSmartWatch() {
            SmartWatch originalSmartWatch = new SmartWatch("Apple Watch", 299.99, apple, "W1234", "OLED", "Metal", "TFT");
            populatedDevices.addTechnologyDevice(originalSmartWatch);
            SmartWatch updatedSmartWatch = new SmartWatch("Apple Watch Series 6", 399.99, apple, "W1234", "OLED", "Metal", "LED");
            boolean updated = populatedDevices.updateSmartWatch("W1234", updatedSmartWatch);
            assertTrue(updated);
            Technology retrieved = populatedDevices.getTechnologyByModelName("Apple Watch Series 6");
            assertEquals(399.99, retrieved.getPrice());
            assertEquals("LED", ((SmartWatch)retrieved).getDisplayType());
        }

        @Test
            void updateExistingSmartBand(){
            SmartBand originalSmartBand = new SmartBand("Fitbit Charge 4", 149.99, samsung, "SB123", "Plastic", "40mm", true);
            populatedDevices.addTechnologyDevice(originalSmartBand);
            SmartBand updatedSmartBand = new SmartBand("Fitbit Charge 5", 159.99, samsung, "SB123", "Plastic", "40mm", false);
            boolean updated = populatedDevices.updateSmartBand("SB123", updatedSmartBand);
            assertTrue(updated);
            Technology retrieved = populatedDevices.getTechnologyByModelName("Fitbit Charge 5");
            assertEquals(159.99, retrieved.getPrice());
            assertFalse(((SmartBand)retrieved).isHeartRateMonitor());

        }

        @Test
        void updateNonExistingTechnology() {
            Tablet updatedTablet = new Tablet("iPad Mini 2", 499.99, apple, "A125", "A12X", 128, "iOS");
            boolean updated = populatedDevices.updateTechnology("Nonexistent Model", updatedTablet);
            assertFalse(updated);
        }

        @Test
        void deleteTechnologyByValidIndex() {
            Tablet tablet = new Tablet("iPad Pro", 999.99, apple, "A123", "A12Z", 128, "iOS");
            populatedDevices.addTechnologyDevice(tablet);
            int initialSize = populatedDevices.numberOfTechnologyDevices();
            Technology removed = populatedDevices.deleteTechnologyByIndex(0);
            assertEquals(initialSize - 1, populatedDevices.numberOfTechnologyDevices());
            assertNotNull(removed);
        }

        @Test
        void deleteTechnologyByInvalidIndex() {
            int initialSize = populatedDevices.numberOfTechnologyDevices();
            Technology removed = populatedDevices.deleteTechnologyByIndex(100); // Assuming 100 is out of bounds
            assertEquals(initialSize, populatedDevices.numberOfTechnologyDevices());
            assertNull(removed);
        }

        @Test
        void deleteTechnologyByIdValidId() {
            populatedDevices.addTechnologyDevice(new Tablet("iPad Pro", 999.99, apple, "T127", "A12Z", 256, "iOS"));
            Technology deleted = populatedDevices.deleteTechnologyById("T127");
            assertNotNull(deleted);
            assertEquals("iPad Pro", deleted.getModelName());
        }

        @Test
        void deleteTechnologyByIdInvalidId() {
            Technology deleted = populatedDevices.deleteTechnologyById("NonExistentID");
            assertNull(deleted);
        }

        @Test
        void retrieveTechnologyByValidId() {
            SmartWatch smartWatch = new SmartWatch("Apple Watch Series 6", 399.99, apple, "W12345", "OLED", "Metal", "44mm");
            populatedDevices.addTechnologyDevice(smartWatch);
            Technology retrieved = populatedDevices.getTechnologyDeviceById("W12345");
            assertNotNull(retrieved);
            assertEquals("Apple Watch Series 6", retrieved.getModelName());
        }

        @Test
        void retrieveTechnologyByInvalidId() {
            SmartWatch smartWatch = new SmartWatch("Apple Watch Series 6", 399.99, apple, "W12345", "OLED", "Metal", "44mm");
            populatedDevices.addTechnologyDevice(smartWatch);
            Technology retrieved = populatedDevices.getTechnologyDeviceById("W5678");
            assertNull(retrieved);
        }


    }

    @Nested
    class ListingMethods {

        @Test
        void listAllReturnsNoTechnologyStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyDevices.numberOfTechnologyDevices());
            assertTrue(emptyDevices.listAllTechnologyDevices().toLowerCase().contains("no technology devices"));
        }

        @Test
        void listAllReturnsTechnologyDevicesStoredWhenArrayListHasTechnologyDevicesStored() {
            assertEquals(4, populatedDevices.numberOfTechnologyDevices());
            String populatedDeviceStr = populatedDevices.listAllTechnologyDevices();
            //checks for objects in the string

            assertTrue(populatedDeviceStr.contains("ID: A123"));
            assertTrue(populatedDeviceStr.contains("ID: W1234"));
            assertTrue(populatedDeviceStr.contains("ID: T1223"));
            assertTrue(populatedDeviceStr.contains("ID: W3535"));

          
        }

        @Test
        void listBySelectedYearReturnsNoTechnologyDevicesWhenNoneExistForEnteredPrice() {
            assertEquals(4, populatedDevices.numberOfTechnologyDevices());
            String populatedDeviceStr = populatedDevices.listAllTechnologyAbovePrice(10000.99);
            assertTrue(populatedDeviceStr.contains("No technology more expensive than"));
        }

        @Test
        void listAllSmartWatchesWhenNonePresent() {
            assertEquals(0, emptyDevices.numberOfSmartWatch());
            String result = emptyDevices.listAllSmartWatches();
            assertTrue(result.toLowerCase().contains("no smart watches"));
        }

        @Test
        void listAllSmartWatchesWhenPresent() {
            SmartWatch sw1 = new SmartWatch("Apple Watch Series 5", 299.99, apple, "W123", "OLED", "Metal", "40mm");
            SmartWatch sw2 = new SmartWatch("Samsung Galaxy Watch", 259.99, samsung, "W124", "AMOLED", "Aluminum", "42mm");
            populatedDevices.addTechnologyDevice(sw1);
            populatedDevices.addTechnologyDevice(sw2);
            String result = populatedDevices.listAllSmartWatches();
            assertTrue(result.contains("Apple Watch Series 5"));
            assertTrue(result.contains("Samsung Galaxy Watch"));
        }

        @Test
        void listAllTabletsWhenNonePresent() {
            assertEquals(0, emptyDevices.numberOfTablets());
            String result = emptyDevices.listAllTablet();
            assertTrue(result.toLowerCase().contains("no tablets"));
        }

        @Test
        void listAllTabletsWhenPresent() {
            Tablet t1 = new Tablet("iPad Air", 599.99, apple, "T123", "A14", 256, "iOS");
            Tablet t2 = new Tablet("Samsung Galaxy Tab S6", 649.99, samsung, "T124", "Snapdragon 855", 128, "Android");
            populatedDevices.addTechnologyDevice(t1);
            populatedDevices.addTechnologyDevice(t2);
            String result = populatedDevices.listAllTablet();
            assertTrue(result.contains("iPad Air"));
            assertTrue(result.contains("Samsung Galaxy Tab S6"));
        }

        @Test
        void listAllSmartBandsWhenNonePresent() {
            assertEquals(0, emptyDevices.numberOfSmartBands());
            String result = emptyDevices.listAllSmartBands();
            assertTrue(result.toLowerCase().contains("no smart bands"));
        }

        @Test
        void listAllSmartBandsWhenPresent() {
            SmartBand sb1 = new SmartBand("Fitbit Charge 4", 149.99, samsung, "SB123", "40mm", "Plastic", true);
            SmartBand sb2 = new SmartBand("Xiaomi Mi Band 5", 39.99, hitachi, "SB124", "35mm", "Silicone", false);
            populatedDevices.addTechnologyDevice(sb1);
            populatedDevices.addTechnologyDevice(sb2);
            String result = populatedDevices.listAllSmartBands();
            assertTrue(result.contains("Fitbit Charge 4"));
            assertTrue(result.contains("Xiaomi Mi Band 5"));
        }

        @Test
        void listAllTechnologyBelowPriceWhenNoneMatch() {
            assertEquals(4, populatedDevices.numberOfTechnologyDevices());
            String result = populatedDevices.listAllTechnologyBelowPrice(50.00);
            assertTrue(result.contains("No technology cheaper than"));
        }

        @Test
        void listAllTechnologyBelowPriceWhenSomeMatch() {
            SmartWatch sw1 = new SmartWatch("Cheap Watch", 49.99, hitachi, "W125", "LCD", "Plastic", "38mm");
            populatedDevices.addTechnologyDevice(sw1);
            String result = populatedDevices.listAllTechnologyBelowPrice(100.00);
            assertTrue(result.contains("Cheap Watch"));
        }

        @Test
        void testListAllTechDevicesByChosenManufacturer() {
            populatedDevices.addTechnologyDevice(new SmartWatch("Apple Watch Series 5", 299.99, apple, "W125", "OLED", "Metal", "44mm"));
            Manufacturer result = populatedDevices.listAllTechDevicesByChosenManufacturer(apple);
            assertNotNull(result);
            assertEquals("Apple", result.getManufacturerName());
        }

        @Test
        void testListAllTechDevicesByNonExistentManufacturer() {
            Manufacturer result = populatedDevices.listAllTechDevicesByChosenManufacturer(new Manufacturer("NonExistent", 9999));
            assertNull(result);
        }

        @Test
        void testListAllTabletsByOperatingSystem() {
            emptyDevices.addTechnologyDevice(new Tablet("iPad Pro", 999.99, apple, "T125", "A12Z", 256, "iPad"));
            String result = emptyDevices.listAllTabletsByOperatingSystem("iPad");
            assertTrue(result.contains("iPad Pro"));
        }

        @Test
        void testListAllTabletsByNonExistentOperatingSystem() {
            String result = populatedDevices.listAllTabletsByOperatingSystem("NonExistentOS");
            assertEquals("No Tablets with operating system: NonExistentOS", result);
        }

    }

    @Nested
    class ReportingMethods {
        @Test
        void topFiveMostExpensiveDevicesWhenListIsNotFull() {
            SmartWatch sw1 = new SmartWatch("Model SW1", 500.00, apple, "SW1", "OLED", "Steel", "44mm");
            SmartWatch sw2 = new SmartWatch("Model SW2", 450.00, samsung, "SW2", "AMOLED", "Aluminum", "42mm");
            emptyDevices.addTechnologyDevice(sw1);
            emptyDevices.addTechnologyDevice(sw2);
            List<Technology> topDevices = emptyDevices.topFiveMostExpensiveTechnology();
            assertEquals(2, topDevices.size());
            assertEquals(sw1, topDevices.get(0));
            assertEquals(sw2, topDevices.get(1));
        }

        @Test
        void topFiveMostExpensiveDevicesWhenListIsFull() {
            for (int i = 0; i < 10; i++) {
                SmartWatch sw = new SmartWatch("Model SW" + i, 1000 - i * 100, samsung, "SW" + i, "AMOLED", "Aluminum", "42mm");
                emptyDevices.addTechnologyDevice(sw);
            }
            List<Technology> topDevices = emptyDevices.topFiveMostExpensiveTechnology();
            assertEquals(5, topDevices.size());
            assertEquals("Model SW0", topDevices.get(0).getModelName());
        }

        @Test
        void reportNumberOfTechnologyByManufacturer() {
            emptyDevices.addTechnologyDevice(new SmartWatch("Apple Watch Series 5", 299.99, apple, "W123", "OLED", "Metal", "40mm"));
            emptyDevices.addTechnologyDevice(new SmartWatch("Apple Watch SE", 279.99, apple, "W124", "OLED", "Metal", "40mm"));
            emptyDevices.addTechnologyDevice(new Tablet("Samsung Galaxy Tab S6", 649.99, samsung, "T124", "Snapdragon 855", 128, "Android"));
            assertEquals(2, emptyDevices.numberOfTechnologyByChosenManufacturer(apple));
            assertEquals(1, emptyDevices.numberOfTechnologyByChosenManufacturer(samsung));
        }

        @Test
        void listTopFiveMostExpensiveSmartWatches() {
            emptyDevices.addTechnologyDevice(new SmartWatch("High End Model", 1500.00, tesla, "SW100", "OLED", "Titanium", "50mm"));
            emptyDevices.addTechnologyDevice(new SmartWatch("Low End Model", 100.00, hitachi, "SW101", "LCD", "Plastic", "38mm"));
            emptyDevices.addTechnologyDevice(new SmartWatch("Mid Range Model", 750.00, tesla, "SW102", "OLED", "Steel", "44mm"));
            List<Technology> topSmartWatches = emptyDevices.topFiveMostExpensiveSmartWatch();
            assertEquals(3, topSmartWatches.size());
            assertEquals("High End Model", topSmartWatches.get(0).getModelName());
            assertEquals("Mid Range Model", topSmartWatches.get(1).getModelName());
        }

        @Test
        void testNumberOfTablets() {
            emptyDevices.addTechnologyDevice(new Tablet("iPad Pro", 999.99, apple, "T129", "A12Z", 256, "iOS"));
            emptyDevices.addTechnologyDevice(new Tablet("Samsung Galaxy Tab S6", 649.99, samsung, "T130", "Snapdragon 855", 128, "Android"));
            assertEquals(2, emptyDevices.numberOfTablets());
        }

        @Test
        void testNumberOfSmartWatches() {
            emptyDevices.addTechnologyDevice(new SmartWatch("Apple Watch Series 5", 399.99, apple, "W127", "OLED", "Metal", "44mm"));
            emptyDevices.addTechnologyDevice(new SmartWatch("Samsung Galaxy Watch", 259.99, samsung, "W128", "AMOLED", "Aluminum", "42mm"));
            assertEquals(2, emptyDevices.numberOfSmartWatch());
        }

        @Test
        void testNumberOfSmartBands() {
            emptyDevices.addTechnologyDevice(new SmartBand("Fitbit Inspire 2", 99.99, samsung, "SB128", "30mm", "Plastic", true));
            emptyDevices.addTechnologyDevice(new SmartBand("Xiaomi Mi Band 5", 39.99, hitachi, "SB129", "35mm", "Silicone", false));
            assertEquals(2, emptyDevices.numberOfSmartBands());
        }
    }

    @Nested
    class SearchingMethods {
        @Test
        void searchTechnologyByModelNameFound() {
            SmartWatch sw1 = new SmartWatch("Apple Watch Series 5", 399.99, apple, "W125", "OLED", "Metal", "44mm");
            populatedDevices.addTechnologyDevice(sw1);
            Technology found = populatedDevices.getTechnologyByModelName("Apple Watch Series 5");
            assertNotNull(found);
            assertEquals("Apple Watch Series 5", found.getModelName());
        }

        @Test
        void searchTechnologyByModelNameNotFound() {
            Technology found = populatedDevices.getTechnologyByModelName("Nonexistent Model");
            assertNull(found);
        }

        @Test
        void searchTechnologyByIdFound() {
            Tablet t1 = new Tablet("iPad Pro", 799.99, apple, "T125", "A12X", 256, "iOS");
            populatedDevices.addTechnologyDevice(t1);
            Technology found = populatedDevices.getTechnologyDeviceById("T125");
            assertNotNull(found);
            assertEquals("iPad Pro", found.getModelName());
        }

        @Test
        void searchTechnologyByIdNotFound() {
            Technology found = populatedDevices.getTechnologyDeviceById("InvalidID");
            assertNull(found);
        }

        @Test
        void listAllByModelNameWhenExists() {
            SmartBand sb1 = new SmartBand("Fitbit Inspire 2", 99.99, samsung, "SB125", "30mm", "Plastic", true);
            populatedDevices.addTechnologyDevice(sb1);
            String result = populatedDevices.listAllByModelName("Fitbit Inspire 2");
            assertTrue(result.contains("Fitbit Inspire 2"));
        }

        @Test
        void listAllByModelNameWhenNotExists() {
            String result = populatedDevices.listAllByModelName("Unknown Model");
            assertEquals("No Technology devices called that model name", result);
        }

        @Test
        void listAllByManufacturerNameWhenExists() {
            SmartBand sb1 = new SmartBand("Fitbit Inspire 2", 99.99, samsung, "SB126", "30mm", "Plastic", true);
            SmartBand sb2 = new SmartBand("Fitbit Inspire 3", 129.99, samsung, "SB127", "35mm", "Plastic", false);
            populatedDevices.addTechnologyDevice(sb1);
            populatedDevices.addTechnologyDevice(sb2);
            String result = populatedDevices.listAllByManufacturerName("Samsung");
            assertTrue(result.contains("Fitbit Inspire 2"));
            assertTrue(result.contains("Fitbit Inspire 3"));
        }

        @Test
        void listAllByManufacturerNameWhenNotExists() {
            String result = populatedDevices.listAllByManufacturerName("Unknown Manufacturer");
            assertEquals("No Technology devices by that manufacturer", result);
        }


    }

    @Nested
    class SortingMethods {

        @Test
        void sortByCostDescendingReOrdersList() {
            assertEquals(4, populatedDevices.numberOfTechnologyDevices());
            //checks the order of the objects in the list

            assertEquals("smart watch1", populatedDevices.getTechnologyByIndex(0).getModelName());
            assertEquals("Smart Watch 12", populatedDevices.getTechnologyByIndex(1).getModelName());

            assertEquals("IPad 123", populatedDevices.getTechnologyByIndex(2).getModelName());
            assertEquals("HiTech Watch", populatedDevices.getTechnologyByIndex(3).getModelName());
            populatedDevices.sortByPriceDescending();

            assertEquals("IPad 123", populatedDevices.getTechnologyByIndex(0).getModelName());
            assertEquals("Smart Watch 12", populatedDevices.getTechnologyByIndex(1).getModelName());
            assertEquals("smart watch1", populatedDevices.getTechnologyByIndex(2).getModelName());

            assertEquals("HiTech Watch", populatedDevices.getTechnologyByIndex(3).getModelName());

        }

        @Test
        void sortByModelNameAscendingReOrdersList() {
            // 添加设备到 populatedDevices 列表中
            emptyDevices.addTechnologyDevice(new Tablet("Z Tablet", 400.00, apple, "T001", "A10", 32, "iOS"));
            emptyDevices.addTechnologyDevice(new SmartWatch("A Watch", 250.00, samsung, "W001", "LCD", "Plastic", "42mm"));
            emptyDevices.addTechnologyDevice(new SmartWatch("M Watch", 350.00, apple, "W002", "OLED", "Metal", "44mm"));
            emptyDevices.sortByModelNameAscending();

            assertEquals("A Watch", emptyDevices.getTechnologyByIndex(0).getModelName());
            assertEquals("M Watch", emptyDevices.getTechnologyByIndex(1).getModelName());
            assertEquals("Z Tablet", emptyDevices.getTechnologyByIndex(2).getModelName());
        }

        @Test
        void sortByModelNameDescendingReOrdersList() {
            // 添加设备到 populatedDevices 列表中
            emptyDevices.addTechnologyDevice(new Tablet("Z Tablet", 400.00, apple, "T002", "A10", 32, "iOS"));
            emptyDevices.addTechnologyDevice(new SmartWatch("A Watch", 250.00, samsung, "W003", "LCD", "Plastic", "42mm"));
            emptyDevices.addTechnologyDevice(new SmartWatch("M Watch", 350.00, apple, "W004", "OLED", "Metal", "44mm"));
            emptyDevices.sortByModelNameDescending();

            assertEquals("Z Tablet", emptyDevices.getTechnologyByIndex(0).getModelName());
            assertEquals("M Watch", emptyDevices.getTechnologyByIndex(1).getModelName());
            assertEquals("A Watch", emptyDevices.getTechnologyByIndex(2).getModelName());
        }

        @Test
        void sortByManufacturerNameAscendingReOrdersList() {
            emptyDevices.addTechnologyDevice(new SmartBand("Fitband Alpha", 199.99, tesla, "SB001", "Medium", "Metal", true));
            emptyDevices.addTechnologyDevice(new SmartBand("Fitband Beta", 129.99, samsung, "SB002", "Small", "Plastic", false));
            emptyDevices.sortByManufacturerNameAscending();

            assertEquals("Samsung", emptyDevices.getTechnologyByIndex(0).getManufacturer().getManufacturerName());
            assertEquals("Tesla", emptyDevices.getTechnologyByIndex(1).getManufacturer().getManufacturerName());
        }

        @Test
        void sortByManufacturerNameDescendingReOrdersList() {
            emptyDevices.addTechnologyDevice(new SmartBand("Fitband Alpha", 199.99, tesla, "SB003", "Medium", "Metal", true));
            emptyDevices.addTechnologyDevice(new SmartBand("Fitband Beta", 129.99, apple, "SB004", "Small", "Plastic", false));
            emptyDevices.sortByManufacturerNameDescending();

            assertEquals("Tesla", emptyDevices.getTechnologyByIndex(0).getManufacturer().getManufacturerName());
            assertEquals("Apple", emptyDevices.getTechnologyByIndex(1).getManufacturer().getManufacturerName());
        }

        @Test
        void testSortByPriceAscending() {
            emptyDevices.addTechnologyDevice(new Tablet("iPad Pro", 999.99, apple, "T128", "A12Z", 256, "iOS"));
            emptyDevices.addTechnologyDevice(new SmartWatch("Apple Watch Series 3", 199.99, apple, "W126", "OLED", "Plastic", "38mm"));
            emptyDevices.sortByPriceAscending();
            assertEquals("Apple Watch Series 3", emptyDevices.getTechnologyByIndex(0).getModelName());
            assertEquals("iPad Pro", emptyDevices.getTechnologyByIndex(1).getModelName());
        }

        @Test
        void testTopFiveMostExpensiveTablet() {
            for (int i = 0; i < 10; i++) {
                populatedDevices.addTechnologyDevice(new Tablet("Tablet " + i, 1000 - i * 100, samsung, "T12" + i, "Processor", 64, "Android"));
            }
            List<Technology> topTablets = populatedDevices.topFiveMostExpensiveTablet();
            assertEquals(5, topTablets.size());
            assertEquals("Tablet 0", topTablets.get(0).getModelName());
        }

        @Test
        void testTopFiveMostExpensiveSmartBand() {
            for (int i = 0; i < 6; i++) {
                populatedDevices.addTechnologyDevice(new SmartBand("Band " + i, 500 - i * 50, samsung, "SB12" + i, "Medium", "Plastic", true));
            }
            List<Technology> topBands = populatedDevices.topFiveMostExpensiveSmartBand();
            assertEquals(5, topBands.size());
            assertEquals("Band 0", topBands.get(0).getModelName());
        }

        @Test
        void testSaveFunction() throws Exception {
            populatedDevices.save();
            File savedFile = new File(populatedDevices.fileName());
            assertTrue(savedFile.exists());
        }

        @Test
        void sortByPriceDescendingDoesntCrashWhenListIsEmpty() {
            assertEquals(0, emptyDevices.numberOfTechnologyDevices());
            emptyDevices.sortByPriceDescending();
        }
    }

}

