package models;

import utils.Utilities;

public class SmartBand extends WearableDevice {
    private boolean heartRateMonitor;

    public SmartBand(String modelName, double price, Manufacturer manufacturer, String id, String material, String size,boolean heartRateMonitor) {
        super(modelName, price, manufacturer, id, material, size);
        this.heartRateMonitor=heartRateMonitor;
    }

    public boolean isHeartRateMonitor() {
        return heartRateMonitor;
    }

    public void setHeartRateMonitor(boolean heartRateMonitor) {
        this.heartRateMonitor = heartRateMonitor;
    }

    public double getInsurancePremium(){
        return Utilities.toTwoDecimalPlaces(getPrice()*0.07);
    }
    public String connectToInternet(){
        return("Connects to the internet via Companion App") ;
    }

    public String toString(){
        String str=super.toString();
        if(heartRateMonitor){
            str+="\n" + "Includes Heart Rate Monitor";
        }
        else{
            str+="No Heart Rate Monitor included";
        }
        str += ", Insurance Premium: " + getInsurancePremium() +
                ", Internet Connection: " + connectToInternet() + "\n";
        return "  Smart Band" + "\n" + str;
    }

}
