package models;

import utils.OperatingSystemUtility;
import utils.Utilities;

public class Tablet extends ComputingDevice {
    private String operatingSystem = "Windows";
    public Tablet(String modelName, double price, Manufacturer manufacturer, String id, String processor, int storage, String operatingSystem) {
        super(modelName, price, manufacturer, id, processor, storage);
        setOperatingSystem(operatingSystem);
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        // 标准化并验证操作系统名称
        String standardizedOS = OperatingSystemUtility.getStandardizedOperatingSystem(operatingSystem);
        if (standardizedOS != null) {
            this.operatingSystem = standardizedOS;
        }
    }

    @Override
    public double getInsurancePremium() {
        // 保险费计算为价格的0.01
        return Utilities.toTwoDecimalPlaces(getPrice() * 0.01);
    }

    @Override
    public String connectToInternet() {
        // 平板通过 Wi-Fi 连接到互联网
        return "Connects to the internet via Wi-Fi";
    }

    @Override
    public String toString() {
        return  "  Tablet" + "\n" +
                super.toString() +
                "\n" + ", Operating System: " + operatingSystem +
                ", Insurance Premium: €" + String.format("%.2f", getInsurancePremium()) + "\n";
    }
}
