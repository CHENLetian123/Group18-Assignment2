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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (Utilities.validStringlength(size, 10)) {
            this.size = size;
        }
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        if (Utilities.validStringlength(material, 20)) {
            this.material = material;
        }
    }



    @Override
    public String toString() {
        return super.toString() +
                "\n" + ", Material: " + material +
                ", Size: " + size;
    }

    // Abstract method
    public abstract double getInsurancePremium();

    public abstract String connectToInternet();
}
