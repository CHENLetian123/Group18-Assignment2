package models;

public abstract class SmartWatch extends WearableDevice{

    public SmartWatch(String modelName, double price, Manufacturer manufacturer, String id, String material, String size) {
        super(modelName, price, manufacturer, id, material, size);
    }

    private String displayType;

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType){
        this.displayType = displayType;
    }

    public String connectToInternet(){

        return null;
    }

    public double getInsurancePremium(){
        return 0.0;
    }

    public String toString(){
          return super.toString() +"Display:" +displayType +
                  "The Internet condition is "+connectToInternet() +
                  "Insurance Premium:" + getInsurancePremium();
    }
}

