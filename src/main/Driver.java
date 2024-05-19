package main;

import controllers.ManufacturerAPI;
import controllers.TechnologyDeviceAPI;

import models.*;
import utils.OperatingSystemUtility;
import utils.ScannerInput;
import utils.Utilities;
import utils.DisplayTypeUtility;

import java.io.File;
import java.util.List;

public class Driver {



        private TechnologyDeviceAPI techAPI;
        private ManufacturerAPI manufacturerAPI;


        public static void main(String[] args) throws Exception {
            new Driver().start();
        }

        public void start() {

            manufacturerAPI = new ManufacturerAPI(new File("manufacturers.xml"));

            //TODO - construct fields √
            techAPI = new TechnologyDeviceAPI(new File("technologyDevices.xml"));

            //TODO - load all data once the serializers are set up √
            try {
                manufacturerAPI.load();
                techAPI.load();
            } catch (Exception e) {
                System.out.println("Error loading data: " + e.getMessage());
            }
            runMainMenu();
        }

    private int mainMenu() {
        System.out.println("""
                         -------Technology Store-------------
                        |  1) Manufacturer CRUD MENU     |
                        |  2) Technology  CRUD MENU      |
                        |  3) Reports MENU               |
                        |--------------------------------|
                        |  4) Search Manufacturers       |
                        |  5) Search Technology Devices  |
                        |  6) Sort Technology Devices    |
                        |--------------------------------|
                        |  10) Save all                  |
                        |  11) Load all                  |
                        |--------------------------------|
                        |  0) Exit                       |
                         --------------------------------""");
        return ScannerInput.readNextInt("==>> ");
    }
    //// search todo by different criteria i.e. look at the list methods and give options based on that.
// sort todo (and give a list of options - not a recurring menu thou)
        private void runMainMenu() {
            int option = mainMenu();
            while (option != 0) {
                switch (option) {
                    case 1->  runManufacturerMenu();
                    case 2->  runTechAPIMenu();
                    case 3->  runReportsMenu();
                    case 4->  searchManufacturers();
                    case 5->  searchTechnologyDevices();
                    case 6->  sortTechnologyDevices();
                    case 10-> saveAll();
                    case 11-> loadAll();
                    //TODO - Add options
                    default ->  System.out.println("Invalid option entered" + option);
                }
                ScannerInput.readNextLine("\n Press the enter key to continue");
                option = mainMenu();
            }
            exitApp();
        }


        private void searchManufacturers(){
            String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");
            Manufacturer manufacturer = manufacturerAPI.getManufacturerByName(manufacturerName);
            if (manufacturer != null) {
                System.out.println(manufacturer);
            } else {
                System.out.println("No such manufacturer exists");
            }
        }

        private void searchTechnologyDevices(){
            String modelName = ScannerInput.readNextLine("Please enter the model name: ");
            Technology tech = techAPI.getTechnologyByModelName(modelName);
            if (tech != null) {
                System.out.println(tech);
            } else {
                System.out.println("No such technology device exists");
            }
        }

        private void sortTechnologyDevices(){
            int option = sortMenu();
            while (option != 0) {
                switch (option) {
                    case 1 -> sortByPriceAscending();
                    case 2 -> sortByPriceDescending();
                    case 3 -> sortByModelNameAscending();
                    case 4 -> sortByModelNameDescending();
                    case 5 -> sortByManufacturerAscending();
                    case 6 -> sortByManufacturerDescending();
                    default -> System.out.println("Invalid option entered" + option);
                }
                ScannerInput.readNextLine("\n Press the enter key to continue");
                option = sortMenu();
            }

        }

        private int sortMenu() {
            System.out.println("""
                --------------Sort Menu---------------
               |  1) Sort by Price Ascending         |
               |  2) Sort by Price Descending        |
               |  3) Sort by Model Name Ascending    |
               |  4) Sort by Model Name Descending   |
               |  5) Sort by Manufacturer Ascending  |
               |  6) Sort by Manufacturer Descending |
               |  0) Return to main menu             |
                ----------------------------""");
            return ScannerInput.readNextInt("==>>");
        }

        private void sortByPriceAscending(){
            techAPI.sortByPriceAscending();
            System.out.println(techAPI.listAllTechnologyDevices());
        }

        private void sortByPriceDescending(){
            techAPI.sortByPriceDescending();
            System.out.println(techAPI.listAllTechnologyDevices());
        }

        private void sortByModelNameAscending(){
            techAPI.sortByModelNameAscending();
            System.out.println(techAPI.listAllTechnologyDevices());
        }

        private void sortByModelNameDescending(){
            techAPI.sortByModelNameDescending();
            System.out.println(techAPI.listAllTechnologyDevices());
        }

        private void sortByManufacturerAscending(){
            techAPI.sortByManufacturerNameAscending();
            System.out.println(techAPI.listAllTechnologyDevices());
        }

        private void sortByManufacturerDescending(){
            techAPI.sortByManufacturerNameDescending();
            System.out.println(techAPI.listAllTechnologyDevices());
        }


        private void saveAll() {
            try {
                manufacturerAPI.save();
                techAPI.save();
                System.out.println("All data saved successfully.");
            } catch (Exception e) {
                System.out.println("Failed to save data: " + e.getMessage());
            }
        }

        private void loadAll() {
            try {
                manufacturerAPI.load();
                techAPI.load();
                System.out.println("All data loaded successfully.");
            } catch (Exception e) {
                System.out.println("Failed to load data: " + e.getMessage());
            }
        }

        private void exitApp(){
            //TODO - save all the data entered √
            saveAll();
            System.out.println("Exiting....");
            System.exit(0);
        }

        //----------------------
        //  Manufacturer Menu Items
        //----------------------
        private int manufacturerMenu() {
            System.out.println("""
                --------Manufacturer Menu---------
               |  1) Add a manufacturer           |
               |  2) Delete a manufacturer        |
               |  3) Update manufacturer details  |
               |  4) List all manufacturers       |
               |  5) Find a manufacturer          |
               |  0) Return to main menu          |
                ----------------------------------""");
            return ScannerInput.readNextInt("==>>");
        }

        private void runManufacturerMenu() {
            int option = manufacturerMenu();
            while (option != 0) {
                switch (option) {
                    case 1 -> addManufacturer();
                    case 2 -> deleteManufacturer();
                    case 3 -> updateManufacturer();
                    case 4 -> System.out.println(manufacturerAPI.listManufacturers());
                    case 5-> findManufacturer();
                    case 6-> listByManufacturerName();
                    default->  System.out.println("Invalid option entered" + option);
                }
                ScannerInput.readNextLine("\n Press the enter key to continue");
                option = manufacturerMenu();
            }
        }

        private void addManufacturer() {
            String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer name: ");
            int manufacturerNumEmployees = ScannerInput.readNextInt("Please enter the number of employees: ");

            if (manufacturerAPI.addManufacturer(new Manufacturer(manufacturerName, manufacturerNumEmployees))){
                System.out.println("Add successful");
            }
            else{
                System.out.println("Add not successful");
            }
        }

        private void deleteManufacturer() {
            String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer name: ");
            if (manufacturerAPI.removeManufacturerByName(manufacturerName) != null){
                System.out.println("Delete successful");
            }
            else{
                System.out.println("Delete not successful");
            }
        }

        private void updateManufacturer(){
            Manufacturer manufacturer = getManufacturerByName();
            if (manufacturer != null){
                int numEmployees= ScannerInput.readNextInt("Please enter number of Employees: ");
                if (manufacturerAPI.updateManufacturer(manufacturer.getManufacturerName(), numEmployees))
                    System.out.println("Number of Employees Updated");
                else
                    System.out.println("Number of Employees NOT Updated");
            }
            else
                System.out.println("Manufacturer name is NOT valid");
        }

        private void findManufacturer(){
            Manufacturer developer = getManufacturerByName();
            if (developer == null){
                System.out.println("No such manufacturer exists");
            }
            else{
                System.out.println(developer);
            }
        }

        private void listByManufacturerName(){
            String manufacturer = ScannerInput.readNextLine("Enter the manufacturer's name:  ");

            System.out.println(manufacturerAPI.listAllByManufacturerName(manufacturer));
        }


        //---------------------
        //  Tech Store Menu
        //---------------------

        private int techAPIMenu() {
            System.out.println("""
                -----Technology Store Menu-----
               | 1) Add a Tech Device           |
               | 2) Delete a Tech Device        |
               | 3) List all Tech Devices       |
               | 4) List all Tablets            |
               | 5) List all SmartWatches       |
               | 6) List all Smart Bands        |
               | 7) Update Tech Device          |
               | 0) Return to main menu         |
                ----------------------------""");
            return ScannerInput.readNextInt("==>>");
        }
    private void runTechAPIMenu() {
        int option = techAPIMenu();
        while (option != 0) {
            switch (option) {
                case 1 ->addTechDevice();
                case 2 -> deleteTechnology();
                case 3 -> System.out.println(techAPI.listAllTechnologyDevices());
                case 4 -> System.out.println(techAPI.listAllTablet());
                case 5 -> System.out.println(techAPI.listAllSmartWatches());
                case 6 -> System.out.println(techAPI.listAllSmartBands());
                case 7 -> updateTechDevice();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = techAPIMenu();
        }
    }



    private void addTechDevice() {
        String model = ScannerInput.readNextLine("Please enter the model name: ");
        double price = ScannerInput.readNextDouble("Please enter the price: ");
        String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");
        Manufacturer manufacturer = manufacturerAPI.getManufacturerByName(manufacturerName);
        if (manufacturer == null) {
            System.out.println("Manufacturer not found.");
            return;
        }

        String id = ScannerInput.readNextLine("Please enter the device ID: ");
        // Check whether the ID already exists
        if (!techAPI.isValidId(id)){
            System.out.println("This device ID already exists! Please use a different device ID or update by this device ID.");
            return;
        }

        char type = ScannerInput.readNextChar("Please enter the type (T=Tablet, S=SmartWatch, B=SmartBand): ");

        switch (type) {
            case 'T', 't'  -> addTablet(model, price, manufacturer, id);
            case 'S', 's' -> addSmartWatch(model, price, manufacturer, id);
            case 'B', 'b' -> addSmartBand(model, price, manufacturer, id);
            default -> System.out.println("Invalid type entered.");
        }
    }

    private void addTablet(String model, double price, Manufacturer manufacturer, String id) {
        System.out.println("Adding a Tablet...");
        String processor = ScannerInput.readNextLine("Please enter the processor: ");
        int storage = ScannerInput.readNextInt("Please enter the storage (GB): ");
        String operatingSystem = ScannerInput.readNextLine("Please enter the operating system: ");
        if (!OperatingSystemUtility.isValidOperatingSystem(operatingSystem)){
            System.out.println("Invalid operating system name.");
            return;
        }
        Tablet tablet = new Tablet(model, price, manufacturer, id, processor, storage, operatingSystem);

        if (techAPI.addTablet(tablet)) {
            System.out.println("Tablet added successfully.");
        } else {
            System.out.println("Failed to add tablet.");
        }
    }

    private void addSmartWatch(String model, double price, Manufacturer manufacturer, String id) {
        System.out.println("Adding a SmartWatch...");
        String material = ScannerInput.readNextLine("Please enter the material: ");
        String size = ScannerInput.readNextLine("Please enter the size: ");
        String displayType = ScannerInput.readNextLine("Please enter the display type: ");
        if (!DisplayTypeUtility.isValidDisplayType(displayType)) {
            System.out.println("Invalid display type.");
            return;
        }

        if (techAPI.addSmartWatch(new SmartWatch(model, price, manufacturer, id, material, size, displayType))) {
            System.out.println("SmartWatch added successfully.");
        } else {
            System.out.println("Failed to add SmartWatch.");
        }
    }

    private void addSmartBand(String model, double price, Manufacturer manufacturer, String id) {
        System.out.println("Adding a SmartBand...");
        String material = ScannerInput.readNextLine("Please enter the material: ");
        String size = ScannerInput.readNextLine("Please enter the size: ");
        char heartRateMonitor = ScannerInput.readNextChar("Does it have a heart rate monitor? (Y/N): ");
        boolean isHeartRateMonitor = Utilities.YNtoBoolean(heartRateMonitor);

        SmartBand smartBand = new SmartBand(model, price, manufacturer, id, material, size, isHeartRateMonitor);
        if (techAPI.addSmartBand(smartBand)) {
            System.out.println("SmartBand added successfully.");
        } else {
            System.out.println("Failed to add SmartBand.");
        }
    }

    private int updateMenu() {
        System.out.println("""
        ------------Update Menu-------------
       | 1) Update by Model Name            |
       | 2) Update by Device ID             |
       | 0) Return to Technology Store Menu |
        -----------------------""");
        return ScannerInput.readNextInt("==>>");
    }

    private void updateTechDevice() {
        int option = updateMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> updateTechDeviceByModelName();
                case 2 -> updateTechDeviceById();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = updateMenu();
        }
    }


    /*private void deleteTechnology() {
        String technologyId = ScannerInput.readNextLine("Please enter the model name: ");
        if (techAPI.removeTechnologyByIndex(technologyId) != null){
            System.out.println("Delete successful");
        }
        else{
            System.out.println("Delete not successful");
        }
    }*/
    private void deleteTechnology() {
        int option = deleteMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> deleteTechDeviceByModelName();
                case 2 -> deleteTechDeviceById();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.readNextLine("\nPress the enter key to continue");
            option = deleteMenu();
        }
    }

    private int deleteMenu() {
        System.out.println("""
    ------------Delete Menu-------------
   | 1) Delete by Model Name            |
   | 2) Delete by Device ID             |
   | 0) Return to Technology Store Menu |
    -----------------------------------""");
        return ScannerInput.readNextInt("==>>");
    }

    private void deleteTechDeviceByModelName() {
        String modelName = ScannerInput.readNextLine("Please enter the model name to delete: ");
        Technology technology = techAPI.getTechnologyByModelName(modelName);
        if (technology == null) {
            System.out.println("Device not found.");
            return;
        }

        // 检查有没有同名的设备
        // Check whether there are any devices with the same name
        int count = 0;
        for (Technology tech : techAPI.getAllTechnologies()) {
            if (tech.getModelName().equalsIgnoreCase(modelName)) {
                count++;
            }
        }

        if (count > 1) {
            System.out.println("Multiple devices found with the same model name. Please use device ID to delete.");
            System.out.println(techAPI.listAllByModelName(modelName));
            return;
        }

        if (techAPI.deleteTechnologyById(technology.getId()) != null) {
            System.out.println("Device deleted successfully.");
        } else {
            System.out.println("Failed to delete device.");
        }
    }

    private void deleteTechDeviceById() {
        String id = ScannerInput.readNextLine("Please enter the device ID to delete: ");
        if (techAPI.isValidId(id)){
            System.out.println("Device not found.");
            return;
        }

        if (techAPI.deleteTechnologyById(id) != null) {
            System.out.println("Device deleted successfully.");
        } else {
            System.out.println("Failed to delete device.");
        }
    }






    public void runReportsMenu(){
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1-> runManufacturerReports();
                case 2-> runTechnologyReports();
                default->  System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = reportsMenu();
        }
    }
    private int reportsMenu() {
        System.out.println("""
                --------Reports Menu ---------
               | 1) Manufacturers Overview    |
               | 2) Technology Overview       |
               | 0) Return to main menu       |
                 -----------------------------""");
        return ScannerInput.readNextInt("==>>");
    }

    private int manufacturerReportsMenu() {
        System.out.println("""
                --------------- Manufacturers Reports Menu  ---------------
               | 1) List Manufacturers                                     |
               | 2) List Manufacturers from a given manufacturer name      |
               | 3) List Manufacturers by a given model name               |
               | 4) Number of Manufacturers                                |
               | 5) Number of Technology devices by chosen manufacture name|
               | 0) Return to main menu                                    |
                 ---------------------------------------------------""");
        return ScannerInput.readNextInt("==>>");
    }
    public void runManufacturerReports() {
        int option = manufacturerReportsMenu();
        while (option != 0) {
            switch (option) {
                case 1-> System.out.println(manufacturerAPI.listManufacturers());
                case 2-> listManufacturersByManufacturerName();
                case 3-> listManufacturersByModelName();
                case 4-> System.out.println("Number of Manufacturers: " + manufacturerAPI.getNumberOfManufacturers());
                case 5-> getNumberOfTechnologyDevicesByManufacturerName();
                default->  System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option =  manufacturerReportsMenu();
        }
    }


    public void listManufacturersByManufacturerName() {
        String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");
        Manufacturer manufacturer = manufacturerAPI.getManufacturerByName(manufacturerName);
        if (manufacturer != null) {
            System.out.println(manufacturerAPI.listAllByManufacturerName(manufacturerName));
        } else {
            System.out.println("No such manufacturer exists");
        }
    }

    public void listManufacturersByModelName() {
        String modelName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");
        String manufacturers = techAPI.getManufacturerNameByModelName(modelName);
        if (manufacturers.equals("No manufacturer found for this model name")) {
            System.out.println("No manufacturer found for this model name!");
        } else {
            System.out.println(manufacturerAPI.listAllByManufacturerName(manufacturers));
        }
    }




    public void runTechnologyReports() {
        int option = technologyReportsMenu();
        while (option != 0) {
            switch (option) {
                case 1-> System.out.println(techAPI.listAllTechnologyDevices());
                case 2-> listTechnologyDevicesByManufacturerName();
                case 3-> listTechnologyDevicesByModelName();
                case 4-> listAllTabletsByOperatingSystem();
                case 5-> System.out.println("Number of Technology Devices: " + techAPI.numberOfTechnologyDevices());
                case 6-> System.out.println("Number of Tablets: " + techAPI.numberOfTablets());
                case 7-> System.out.println("Number of SmartWatches: " + techAPI.numberOfSmartWatch());
                case 8-> System.out.println("Number of Smart Bands: " + techAPI.numberOfSmartBands());
                case 9-> printTopFiveMostExpensiveTechnology();
                case 10-> printTopFiveMostExpensiveTablets();
                case 11-> printTopFiveMostExpensiveSmartWatches();
                case 12-> printTopFiveMostExpensiveSmartBands();
                case 13-> listAlltheTechnologyAbovePrice();
                case 14-> listAlltheTechnologyBelowPrice();
                default->  System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option =  technologyReportsMenu();
        }
    }

    private int technologyReportsMenu() {
        System.out.println("""
                --------------- Technology Reports Menu  ---------------
               | 1) List Technology Devices                             |
               | 2) List Technology Devices by a given manufacturer name|
               | 3) List Technology Devices by a given model name       |
               | 4) List all Tablets by operating system name           |
               | 5) Number of Technology Devices                        |
               | 6) Number of Tablets                                   |
               | 7) Number of SmartWatches                              |
               | 8) Number of Smart Bands                               |
               | 9) List the Top five most expensive Technology devices |
               | 10) List the Top five most expensive Tablets            |
               | 11) List the Top five most expensive Smartwatches      |
               | 12) List the Top five most expensive Smart Bands       |
               | 13) List all the Technology devices above the price    |
               | 14) List all the Technology devices below the price    |
               | 0) Return to main menu                                 |
                 ---------------------------------------------------""");
        return ScannerInput.readNextInt("==>>");
    }

    public void listTechnologyDevicesByManufacturerName() {
        String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");

        if (manufacturerAPI.isValidManufacturer(manufacturerName)){
            System.out.println(techAPI.listAllByManufacturerName(manufacturerName));
        } else {
            System.out.println("Invalid Manufacturer name!");
            System.out.println(manufacturerAPI.listManufacturers());
        }
    }

    public void listTechnologyDevicesByModelName() {
        String modelName = ScannerInput.readNextLine("Please enter the model name: ");
        System.out.println(techAPI.listAllByModelName(modelName));
    }

    public void listAllTabletsByOperatingSystem() {
        String operatingSystem = ScannerInput.readNextLine("Please enter the operating system: ");
        if (OperatingSystemUtility.isValidOperatingSystem(operatingSystem)) {
            System.out.println(techAPI.listAllTabletsByOperatingSystem(operatingSystem));
        } else {
            System.out.println("Invalid operating system name.");
        }
    }

    public void printTopFiveMostExpensiveTechnology() {
        List<Technology> topFive = techAPI.topFiveMostExpensiveTechnology();
        System.out.println("Top Five Most Expensive Technology Devices:");
        printTechnologyList(topFive);
    }

    public void printTopFiveMostExpensiveTablets() {
        List<Technology> topFive = techAPI.topFiveMostExpensiveTablet();
        System.out.println("Top Five Most Expensive Tablets:");
        printTechnologyList(topFive);
    }

    public void printTopFiveMostExpensiveSmartWatches() {
        List<Technology> topFive = techAPI.topFiveMostExpensiveSmartWatch();
        System.out.println("Top Five Most Expensive SmartWatches:");
        printTechnologyList(topFive);
    }

    public void printTopFiveMostExpensiveSmartBands() {
        List<Technology> topFive = techAPI.topFiveMostExpensiveSmartBand();
        System.out.println("Top Five Most Expensive Smart Bands:");
        printTechnologyList(topFive);
    }

    public void listAlltheTechnologyAbovePrice(){
            Double price = ScannerInput.readNextDouble("Please enter a price: ");
            System.out.println(techAPI.listAllTechnologyAbovePrice(price));
    }

    public void listAlltheTechnologyBelowPrice(){
        Double price = ScannerInput.readNextDouble("Please enter a price: ");
        System.out.println(techAPI.listAllTechnologyBelowPrice(price));
    }


//todo update methods counting methods
        //counting methods
        public void getNumberOfTechnologyDevicesByManufacturerName() {
            String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");
            if (manufacturerAPI.isValidManufacturer(manufacturerName)){
                Manufacturer manufacturer = manufacturerAPI.getManufacturerByName(manufacturerName);
                int count = techAPI.numberOfTechnologyByChosenManufacturer(manufacturer);
                if (count == 0){
                    System.out.println("No Technology devices by this manufacturer!");
                }
                else {
                    System.out.println("There are " + count + "Technology devices by this manufacturer.");
                }
            }
            else {
                System.out.println("Invalid Manufacturer name!");
                System.out.println(manufacturerAPI.listManufacturers());
            }
        }

        //update methods
        private void updateTechDeviceByModelName() {
            String oldModelName = ScannerInput.readNextLine("Please enter the model name to update: ");
            Technology technology = techAPI.getTechnologyByModelName(oldModelName);
            if (technology == null) {
                System.out.println("Device not found.");
                return;
            }

            // 检查有没有同名的设备
            // Check whether there are any devices with the same name
            int count = 0;
            for (Technology tech : techAPI.getAllTechnologies()) {
                if (tech.getModelName().equalsIgnoreCase(oldModelName)) {
                    count++;
                }
            }

            if (count > 1) {
                System.out.println("Multiple devices found with the same model name. Please use device ID to update.");
                System.out.println(techAPI.listAllByModelName(oldModelName));
                return;
            }

            System.out.println("Device found: "+ "\n"  + techAPI.listAllByModelName(oldModelName));

            String oldId = technology.getId();
            String newModelName = ScannerInput.readNextLine("Please enter the new model name: ");
            double price = ScannerInput.readNextDouble("Please enter the new price: ");
            String manufacturerName = ScannerInput.readNextLine("Please enter the new manufacturer's name: ");
            Manufacturer manufacturer = manufacturerAPI.getManufacturerByName(manufacturerName);
            if (manufacturer == null) {
                System.out.println("Manufacturer not found.");
                return;
            }
            String id = ScannerInput.readNextLine("Please enter the new device ID: ");
            /*char type = ScannerInput.readNextChar("Please enter the type (T=Tablet, S=SmartWatch, B=SmartBand): ");

            switch (type) {
                case 'T', 't' -> updateTablet(oldModelName, newModelName, price, manufacturer, id);
                case 'S', 's' -> updateSmartWatch(oldModelName, newModelName, price, manufacturer, id);
                case 'B', 'b' -> updateSmartBand(oldModelName, newModelName, price, manufacturer, id);
                default -> System.out.println("Invalid type entered.");
            }
        }*/
            if (technology instanceof Tablet) {
                System.out.println("Updating a Tablet...");
                updateTablet(oldId, newModelName, price, manufacturer, id);
            } else if (technology instanceof SmartWatch) {
                System.out.println("Updating a SmartWatch...");
                updateSmartWatch(oldId, newModelName, price, manufacturer, id);
            } else if (technology instanceof SmartBand) {
                System.out.println("Updating a SmartBand...");
                updateSmartBand(oldId, newModelName, price, manufacturer, id);
            } else {
                System.out.println("Invalid type of technology device.");
            }
        }

    private void updateTechDeviceById() {
        String oldId = ScannerInput.readNextLine("Please enter the device ID to update: ");
        if (techAPI.isValidId(oldId)){
            System.out.println("Device not found.");
            return;
        }
        Technology technology = techAPI.getTechnologyDeviceById(oldId);
        /*if (technology == null) {
            System.out.println("Device not found.");
            return;
        }*/
        System.out.println("Device found: "+ "\n" + techAPI.getTechnologyDeviceById(oldId));

        String newModelName = ScannerInput.readNextLine("Please enter the new model name: ");
        double price = ScannerInput.readNextDouble("Please enter the new price: ");
        String manufacturerName = ScannerInput.readNextLine("Please enter the new manufacturer's name: ");
        Manufacturer manufacturer = manufacturerAPI.getManufacturerByName(manufacturerName);
        if (manufacturer == null) {
            System.out.println("Manufacturer not found.");
            return;
        }
        String id = ScannerInput.readNextLine("Please enter the new device ID: ");

        if (technology instanceof Tablet) {
            System.out.println("Updating a Tablet...");
            updateTablet(oldId, newModelName, price, manufacturer, id);
        } else if (technology instanceof SmartWatch) {
            System.out.println("Updating a SmartWatch...");
            updateSmartWatch(oldId, newModelName, price, manufacturer, id);
        } else if (technology instanceof SmartBand) {
            System.out.println("Updating a SmartBand...");
            updateSmartBand(oldId, newModelName, price, manufacturer, id);
        } else {
            System.out.println("Invalid type of technology device.");
        }
    }


    private void updateTablet(String oldId, String newModelName, double price, Manufacturer manufacturer, String id) {
            String processor = ScannerInput.readNextLine("Please enter the new processor: ");
            int storage = ScannerInput.readNextInt("Please enter the new storage (GB): ");
            String operatingSystem = ScannerInput.readNextLine("Please enter the new operating system: ");
            Tablet updatedTablet = new Tablet(newModelName, price, manufacturer, id, processor, storage, operatingSystem);
            if (techAPI.updateTablet(oldId, updatedTablet)) {
                System.out.println("Tablet updated successfully.");
            } else {
                System.out.println("Failed to update tablet.");
            }
        }

        private void updateSmartWatch(String oldId, String newModelName, double price, Manufacturer manufacturer, String id) {
            String material = ScannerInput.readNextLine("Please enter the new material: ");
            String size = ScannerInput.readNextLine("Please enter the new size: ");
            String displayType = ScannerInput.readNextLine("Please enter the new display type: ");
            if (!DisplayTypeUtility.isValidDisplayType(displayType)) {
                System.out.println("Invalid display type.");
                return;
            }
            SmartWatch updatedSmartWatch = new SmartWatch(newModelName, price, manufacturer, id, material, size, displayType);
            if (techAPI.updateSmartWatch(oldId, updatedSmartWatch)) {
                System.out.println("SmartWatch updated successfully.");
            } else {
                System.out.println("Failed to update SmartWatch.");
            }
        }

        private void updateSmartBand(String oldId, String newModelName, double price, Manufacturer manufacturer, String id) {
            String material = ScannerInput.readNextLine("Please enter the new material: ");
            String size = ScannerInput.readNextLine("Please enter the new size: ");
            char heartRateMonitor = ScannerInput.readNextChar("Does it have a heart rate monitor? (Y/N): ");
            boolean isHeartRateMonitor = Utilities.YNtoBoolean(heartRateMonitor);
            SmartBand updatedSmartBand = new SmartBand(newModelName, price, manufacturer, id, material, size, isHeartRateMonitor);
            if (techAPI.updateSmartBand(oldId, updatedSmartBand)) {
                System.out.println("SmartBand updated successfully.");
            } else {
                System.out.println("Failed to update SmartBand.");
            }
        }



        //---------------------
        //  General Menu Items
        //---------------------

//TODO - write all the methods that are called from your menu
       /* private void callAllMethods(){
                 printTopFiveMostExpensiveTablets
                 runManufacturerReports
                 deleteMenu
                 getManufacturerByName
                 updateMenu
                 sortByPriceDescending
                 getNumberOfTechnologyDevicesByManufacturerName
                 sortTechnologyDevices
                 addSmartWatch
                 sortByModelNameDescending
                 techAPIMenu
                 updateTechDeviceByld
                 runTechnologyReports
                 sortMenu
                 addTechDevice
                 listAllTabletsByOperatingSystem
                 reportsMenu
                 runManufacturerMenu
                 manufacturerMenu
                 printTechnologyList
                 exitApp
                 loadAll
                 runReportsMenu
                 addSmartBand
                 listByManufacturerName
                 printTopFiveMostExpensiveSmartWatches
                 deleteTechDeviceByld
                 updateSmartWatch
                 deleteManufacturer
                 runTechAPIMenu
                 main (String [])
                 listTechnologyDevicesByModelName
                 start
                 technologyReportsMenu
                 updateTechDeviceByModelName
                 searchTechnologyDevices
                 listTechnologyDevicesByManufacturerName
                 callAllMethods
                 sortByManufacturerDescending
                 listManufacturersByModelName
                 findManufacturer
                 sortByPriceAscending
                 sortByModelNameAscending
                 listAlltheTechnologyAbovePrice
                 deleteTechnology
                 deleteTechDeviceByModelName
                 sortByManufacturerAscending
                 searchManufacturers
                 listManufacturersByManufacturerName
                 updateManufacturer
                 updateSmartBand
                 saveAll
                 listAlltheTechnologyBelowPrice
                 addManufacturer
                 addTablet
                 updateTablet
                 mainMenu
                 printTopFiveMostExpensiveSmartBands
                 manufacturerRebortsMenu
                 runMainMenu
                 updateTechDevice
                 printTopFiveMostExpensiveTechnology
                 }*/


        //---------------------
        //  Helper Methods
        //---------------------

//TODO- write any helper methods that are required
        // 辅助方法：打印技术设备列表
        private void printTechnologyList(List<Technology> list) {
            int num = 1;
            for (Technology tech : list) {
                System.out.println("Top " + num + ": " + tech);
                num++;
            }
        }



        private Manufacturer getManufacturerByName(){
            String manufacturerName = ScannerInput.readNextLine("Please enter the manufacturer's name: ");
            if (manufacturerAPI.isValidManufacturer(manufacturerName)){
                return manufacturerAPI.getManufacturerByName(manufacturerName);
            }
            else{
                return null;
            }
        }

    }

