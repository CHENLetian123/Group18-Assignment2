package models;

import utils.DisplayTypeUtility;
import utils.Utilities;

public class SmartWatch extends WearableDevice{
    private String displayType;

    public SmartWatch(String modelName, double price, Manufacturer manufacturer, String id, String material, String size,String displayType) {
        super(modelName, price, manufacturer, id, material, size);
        setDisplayType(displayType);
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType){
        if (DisplayTypeUtility.isValidDisplayType(displayType)){
            this.displayType = displayType;
        }
        else {
            this.displayType = "TCD";
        }
    }
    @Override
    public String connectToInternet(){
        return "Connects to the Internet via Bluetooth!";
    }

    public double getInsurancePremium(){
        return Utilities.toTwoDecimalPlaces(getPrice() * 0.06);
    }

    public String toString(){
          return  "  Smart Watch" + "\n" +
                  super.toString() +
                  "\n" + ", Display: " +displayType +
                  ", The Internet condition is "+connectToInternet() +
                  ", Insurance Premium: €" + getInsurancePremium() + "\n";
    }
}

