package models;

import utils.DisplayTypeUtility;
import utils.Utilities;

public class SmartWatch extends WearableDevice{

    public SmartWatch(String modelName, double price, Manufacturer manufacturer, String id, String material, String size,String processor) {
        super(modelName, price, manufacturer, id, material, size);
        setDisplayType(displayType);
    }

    private String displayType;

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
          return super.toString() +"Display:" +displayType +
                  "The Internet condition is "+connectToInternet() +
                  "Insurance Premium:" + getInsurancePremium();
    }
}

