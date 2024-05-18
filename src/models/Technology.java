package models;

import utils.Utilities;

public abstract class Technology {
    private Manufacturer manufacturer;
    private String id = "unknown";
    private String modelName;
    private double price = 20;

    public Technology(String modelName, double price, Manufacturer manufacturer, String id) {
        this.modelName = Utilities.truncateString(modelName, 30);
        setPrice(price);
        setManufacturer(manufacturer);
        setId(id);
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
            if (Utilities.validStringlength(modelName, 30)) {
                this.modelName = modelName;
            }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 20) {
            this.price = Utilities.toTwoDecimalPlaces(price);
        }
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        if (manufacturer != null) {
            this.manufacturer = manufacturer;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (Utilities.validStringlength(id, 10)){
            this.id = id;
        }
    }


    public abstract double getInsurancePremium();

    public abstract String connectToInternet();

    @Override
    public String toString() {

        return  "  Model: " + modelName +
                ", Price: €" + price +
                ", Manufacturer Details: Manufacturer{manufacturerName='" + manufacturer.getManufacturerName() + "'" +
                ", numEmployees=" + manufacturer.getNumEmployees() + (manufacturer.getNumEmployees() ==1 ? " employee" : " employees") + "}" +
                ", ID: " + id;
        //"Model: Galaxy Tab S7, Price: €799.99, Manufacturer Details: Manufacturer{manufacturerName='Samsung', numEmployees=333 employees}, ID: 123456"
        //"Model: Galaxy Tab S7 version 1 c.0946, Price: €20.0, Manufacturer Details: Manufacturer{manufacturerName='ABCDEFGHIJKLMNOPQRST', numEmployees=1 employee}, ID: unknown"
    }
}
