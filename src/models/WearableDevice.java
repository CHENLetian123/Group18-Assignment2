package models;

import utils.Utilities;

public abstract class WearableDevice extends Technology {
    private String material;
    private String size;

    public WearableDevice(String modelName, double price, Manufacturer manufacturer, String id, String material, String size) {
        super(modelName, price, manufacturer, id);
        setMaterial(material);
        setSize(size);
    }

    // Getter for material
    public String getMaterial() {
        return material;
    }

    // Setter for material
    public void setMaterial(String material) {
        if (Utilities.validStringlength(material, 20)) {
            this.material = material;
        }
    }

    // Getter for size
    public String getSize() {
        return size;
    }

    // Setter for size
    public void setSize(String size) {
        if (Utilities.validStringlength(size, 10)) {
            this.size = size;
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Material: " + material +
                ", Size: " + size;
    }

    // Abstract method to be implemented by subclasses
    public abstract double getInsurancePremium();

    // Abstract method to be implemented by subclasses
    public abstract String connectToInternet();
}
