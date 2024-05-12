package models;

import utils.Utilities;

public abstract class Technology {
    private Manufacturer manufacturer;
    private String id;
    private String modelName;
    private double price;

    public Technology(String modelName, double price, Manufacturer manufacturer, String id) {
        setModelName(modelName);
        setPrice(price);
        setManufacturer(manufacturer);
        setId(id);
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        if (Utilities.validStringlength(modelName, 30)) {
            this.modelName = Utilities.truncateString(modelName, 30);
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
        if (Utilities.validStringlength(id, 10)) {
            this.id = Utilities.truncateString(id, 10);
        } else {
            this.id = "unknown";
        }
    }

    public abstract double getInsurancePremium();

    public abstract String connectToInternet();

    @Override
    public String toString() {
        return "ID: " + id +
                ", Model Name: " + modelName +
                ", Price: €" + price +
                ", Manufacturer: " + manufacturer.getManufacturerName() +
                " (" + manufacturer.getNumEmployees() + " employees)";
    }
}
