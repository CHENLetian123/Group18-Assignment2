package controllers;

import models.*;

import utils.DisplayTypeUtility;
import utils.ISerializer;
import utils.OperatingSystemUtility;

import utils.Utilities;

import java.io.*;
import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import static utils.Utilities.isValidIndex;


//TODO - ensure that this class implements iSerializer
public class TechnologyDeviceAPI implements ISerializer{
    //TODO - create 2 fields
    private List<Technology> technologyList = new ArrayList<>();
    private File file;



    //TODO - create constructor
    public TechnologyDeviceAPI(File file) {
        this.file = file;
    }

   //TODO - CRUD Methods

    // Create methods
    public boolean addTechnologyDevice(Technology technology) {
        if (isValidId(technology.getId())) {
            technologyList.add(technology);
            return true;
        }
        return false;
    }

    public boolean addSmartWatch(SmartWatch smartWatch){
        smartWatch.setDisplayType(smartWatch.getDisplayType());

        technologyList.add(smartWatch);
        return true;
    }

    public boolean addTablet(Tablet tablet){
        tablet.setOperatingSystem(tablet.getOperatingSystem());
        tablet.setStorage(tablet.getStorage());
        tablet.setOperatingSystem(tablet.getOperatingSystem());

        technologyList.add(tablet);
        return true;
    }

    public boolean addSmartBand(SmartBand smartBand){

        technologyList.add(smartBand);
        return true;
    }




    //TODO - Number methods
    public int numberOfTechnologyByChosenManufacturer(Manufacturer manufacturer) {
        int count = 0;
        for (Technology tech : technologyList) {
            if (tech.getManufacturer().equals(manufacturer)) {
                count++;
            }
        }
        return count;
    }

    // 计数平板数量
    public int numberOfTablets() {
        int count = 0;
        for (Technology tech : technologyList) {
            if (tech instanceof Tablet) {
                count++;
            }
        }
        return count;
    }

    // 计数智能手表数量
    public int numberOfSmartWatches() {
        int count = 0;
        for (Technology tech : technologyList) {
            if (tech instanceof SmartWatch) {
                count++;
            }
        }
        return count;
    }

    // 计数智能手环数量
    public int numberOfSmartBands() {
        int count = 0;
        for (Technology tech : technologyList) {
            if (tech instanceof SmartBand) {
                count++;
            }
        }
        return count;
    }

    // TODO Read/list methods
    public String listAllTablets() {
        String listAllTablets = "";
        for (Technology tech : technologyList) {
            if (tech instanceof Tablet) {
                listAllTablets += tech + "\n";
            }
        }
        if (listAllTablets.equals("")) {
            return "No Tablets";
        } else {
            return listAllTablets;
        }
    }

    public String listAllSmartWatches() {
        String listAllSmartWatches = "";
        for (Technology tech : technologyList) {
            if (tech instanceof SmartWatch) {
                listAllSmartWatches += tech + "\n";
            }
        }
        if (listAllSmartWatches.equals("")) {
            return "No Smart Watches";
        } else {
            return listAllSmartWatches;
        }
    }

    public String listAllSmartBands() {
        String listAllSmartBands = "";
        for (Technology tech : technologyList) {
            if (tech instanceof SmartBand) {
                listAllSmartBands += tech + "\n";
            }
        }
        if (listAllSmartBands.equals("")) {
            return "No Smart Bands";
        } else {
            return listAllSmartBands;
        }
    }

    public String getManufacturerNameByModelName(String modelName) {
        for (Technology tech : technologyList) {
            if (tech.getModelName().equalsIgnoreCase(modelName)) {
                return tech.getManufacturer().getManufacturerName();
            }
        }
        return "No manufacturer found for this model name";
    }

    // 列出所有技术设备
    public String listAllTechnologyDevices() {
        String listAllTechnologyDevices = "";
        for (Technology tech : technologyList) {
            listAllTechnologyDevices += (technologyList.indexOf(tech) + 1) + ": "+ "\n" + tech + "\n";
        }
        if (listAllTechnologyDevices.equals("")) {
            return "No Technology Devices";
        } else {
            return listAllTechnologyDevices;
        }
    }

    public String listAllByModelName(String modelName){
        if (!technologyList.isEmpty()) {
            String listTechnology = "";
            for (Technology tech : technologyList) {
                if (tech.getModelName().equalsIgnoreCase(modelName))
                    listTechnology += tech + "\n";
            }
            if (listTechnology.equals("")) {
                return "No Technology devices called that model name";
            } else {
                return listTechnology;
            }
        }
        else return "There are no Technology devices in the list.";
    }

    public  String listAllByManufacturerName(String manuName){
        if (!technologyList.isEmpty()) {
            String listTechnology = "";
            for (Technology tech : technologyList) {
                if (tech.getManufacturer().getManufacturerName().equalsIgnoreCase(manuName))
                    listTechnology += technologyList.indexOf(tech) + ": " + tech + "\n";
        }
            if (listTechnology.equals("")) {
                return "No Technology devices by that manufacturer";
            } else {
                return listTechnology;
            }
        }
        else return "There are no Technology devices in the list.";
    }


    /*public String listAllByManufacturerName(String manuName){
        if (!manufacturers.isEmpty()) {
            String listManufacturers = "";
            for (Manufacturer manufacturer : manufacturers) {
                if (manufacturer.getManufacturerName().equalsIgnoreCase(manuName))
                    listManufacturers += manufacturers.indexOf(manufacturer) + ": " + manufacturer + "\n";
            }
            if (listManufacturers.equals("")) {
                return "No manufacturers of that name";
            } else {
                return listManufacturers;
            }
        }
        else return "There are no manufacturers in the list.";
    }*/

    public String listAllTechnologyAbovePrice(double price) {
        if (technologyList.isEmpty()) {
            return "No Technology Devices in the store";
        } else {
            String result = "";
            for (int i = 0; i < technologyList.size(); i++) {
                if (technologyList.get(i).getPrice() > price) {
                    result += i + ": " + technologyList.get(i) + "\n";
                }
            }
            if (result.equals("")) {
                return "No technology more expensive than: " + price;
            } else {
                return result;
            }
        }
    }

    public List<Technology> getAllTechnologies() {
        return new ArrayList<>(technologyList);
    }



    //the following is isValidId can be updated
    //to suit your code
    /*public boolean isValidId(String id) {
        for (Technology techDev : technologyList) {
            if (techDev.getId().equals(id))
                return false;
        }
            return true;
        }
*/
    public boolean isValidId(String id) {
        for (Technology techDev : technologyList) {
            if (techDev.getId().equals(id))
                return false;
        }
        return true;
    }

    //TODO get Technology methods
    public Technology getTechnologyByIndex(int index) {
        if (Utilities.isValidIndex(technologyList, index)) {
            return technologyList.get(index);
        }
        return null;
    }


    public Technology getTechnologyById(String id) {
        for (Technology tech : technologyList) {
            if (tech.getId().equals(id)) {
                return tech;
            }
        }
        return null;
    }

    public Technology getTechnologyByModelName(String modelName) {
        for (Technology tech : technologyList) {
            if (tech.getModelName().equalsIgnoreCase(modelName)) {
                return tech;
            }
        }
        return null;
    }

    public int numberOfTechnologyDevices() {
        return technologyList.size();
    }


    /*public int retrieveManufacturerIndex(String manufacturerName) {
        int index = 0;
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getManufacturerName().equalsIgnoreCase(manufacturerName)) {
                return index;
            }
            index++;
        }
        return -1;
    }*/
    public int retrieveTechnologyIndex(String ID) {
        int index = 0;
        for (Technology tech : technologyList) {
            if (tech.getId().equalsIgnoreCase(ID)) {
                return index;
            }
            index++;
        }
        return -1;
    }
   //TODO - delete methods
    public Technology removeTechnologyByID(String ID) {
        int index = retrieveTechnologyIndex(ID);
        if (index != -1) {
            return technologyList.remove(index);
        }
        return null;
    }
   /*public Manufacturer removeManufacturerByName(String manufacturerName){
       int index = retrieveManufacturerIndex(manufacturerName);
       if (index != -1) {
           return manufacturers.remove(index);
       }
       return null;
   }*/


    // update methods
    public boolean updateTechnology(String oldModelName, Technology updateTech) {
        Technology foundTech = getTechnologyByModelName(oldModelName);
        if (foundTech != null) {
            foundTech.setModelName(updateTech.getModelName());
            foundTech.setPrice(updateTech.getPrice());
            foundTech.setManufacturer(updateTech.getManufacturer());
            foundTech.setId(updateTech.getId());
            return true;
        }
        return false;
    }


    public boolean updateSmartWatch(String oldId, SmartWatch updateSmartWatch) {
        SmartWatch foundSmartWatch = (SmartWatch) getTechnologyById(oldId);
        if (foundSmartWatch != null) {
            foundSmartWatch.setModelName(updateSmartWatch.getModelName());
            foundSmartWatch.setPrice(updateSmartWatch.getPrice());
            foundSmartWatch.setManufacturer(updateSmartWatch.getManufacturer());
            foundSmartWatch.setId(updateSmartWatch.getId());
            foundSmartWatch.setDisplayType(updateSmartWatch.getDisplayType());
            foundSmartWatch.setMaterial(updateSmartWatch.getMaterial());
            foundSmartWatch.setSize(updateSmartWatch.getSize());
            return true;
        }
        return false;
    }

    public boolean updateTablet(String oldId, Tablet updateTablet) {
        Tablet foundTablet = (Tablet) getTechnologyById(oldId);
        if (foundTablet != null) {
            foundTablet.setModelName(updateTablet.getModelName());
            foundTablet.setPrice(updateTablet.getPrice());
            foundTablet.setManufacturer(updateTablet.getManufacturer());
            foundTablet.setId(updateTablet.getId());
            foundTablet.setProcessor(updateTablet.getProcessor());
            foundTablet.setStorage(updateTablet.getStorage());
            foundTablet.setOperatingSystem(updateTablet.getOperatingSystem());
            return true;
        }
        return false;
    }


    public boolean updateSmartBand(String oldId, SmartBand updateSmartBand) {
        SmartBand foundSmartBand = (SmartBand) getTechnologyById(oldId);
        if (foundSmartBand != null) {
            foundSmartBand.setModelName(updateSmartBand.getModelName());
            foundSmartBand.setPrice(updateSmartBand.getPrice());
            foundSmartBand.setManufacturer(updateSmartBand.getManufacturer());
            foundSmartBand.setId(updateSmartBand.getId());
            foundSmartBand.setHeartRateMonitor(updateSmartBand.isHeartRateMonitor());
            foundSmartBand.setSize(updateSmartBand.getSize());
            foundSmartBand.setMaterial(updateSmartBand.getMaterial());
            return true;
        }
        return false;
    }


    //TODO - sort methods
    private void swapTechnology(int i, int j) {
        Technology temp = technologyList.get(i);
        technologyList.set(i, technologyList.get(j));
        technologyList.set(j, temp);
    }

    // 按价格降序排序
    public void sortByPriceDescending() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < technologyList.size() - 1; i++) {
                if (technologyList.get(i).getPrice() < technologyList.get(i + 1).getPrice()) {
                    swapTechnology(i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
    }


    // 按价格升序排序
    public void sortByPriceAscending() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < technologyList.size() - 1; i++) {
                if (technologyList.get(i).getPrice() > technologyList.get(i + 1).getPrice()) {
                    swapTechnology(i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
    }


    // 按型号名称升序排序
    public void sortByModelNameAscending() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < technologyList.size() - 1; i++) {
                if (technologyList.get(i).getModelName().compareTo(technologyList.get(i + 1).getModelName()) > 0) {
                    swapTechnology(i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
    }


    // 按型号名称降序排序
    public void sortByModelNameDescending() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < technologyList.size() - 1; i++) {
                if (technologyList.get(i).getModelName().compareTo(technologyList.get(i + 1).getModelName()) < 0) {
                    swapTechnology(i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
    }


    // 按制造商名称升序排序
    public void sortByManufacturerNameAscending() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < technologyList.size() - 1; i++) {
                if (technologyList.get(i).getManufacturer().getManufacturerName().compareTo(technologyList.get(i + 1).getManufacturer().getManufacturerName()) > 0) {
                    swapTechnology(i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
    }


    // 按制造商名称降序排序
    public void sortByManufacturerNameDescending() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < technologyList.size() - 1; i++) {
                if (technologyList.get(i).getManufacturer().getManufacturerName().compareTo(technologyList.get(i + 1).getManufacturer().getManufacturerName()) < 0) {
                    swapTechnology(i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
    }





    //TODO Top 5 methods

    // List the Top five most expensive Technology devices
    public List<Technology> topFiveMostExpensiveTechnology() {
        List<Technology> sortedList = new ArrayList<>(technologyList);
        sortTechnologiesByPriceDescending(sortedList);
        return sortedList.subList(0, Math.min(5, sortedList.size()));
    }

    // List the Top five most expensive Tablets
    public List<Technology> topFiveMostExpensiveTablets() {
        List<Technology> sorted = new ArrayList<>();
        for (Technology tech : technologyList) {
            if (tech instanceof Tablet) {
                sorted.add(tech);
            }
        }
        sortTechnologiesByPriceDescending(sorted);
        return sorted.subList(0, Math.min(5, sorted.size()));
    }

    // List the Top five most expensive Smartwatches
    public List<Technology> topFiveMostExpensiveSmartWatches() {
        List<Technology> sorted = new ArrayList<>();
        for (Technology tech : technologyList) {
            if (tech instanceof SmartWatch) {
                sorted.add(tech);
            }
        }
        sortTechnologiesByPriceDescending(sorted);
        return sorted.subList(0, Math.min(5, sorted.size()));
    }

    // List the Top five most expensive Smart Bands
    public List<Technology> topFiveMostExpensiveSmartBands() {
        List<Technology> sorted = new ArrayList<>();
        for (Technology tech : technologyList) {
            if (tech instanceof SmartBand) {
                sorted.add(tech);
            }
        }
        sortTechnologiesByPriceDescending(sorted);
        return sorted.subList(0, Math.min(5, sorted.size()));
    }


    // 辅助方法：按价格降序排序
    private void sortTechnologiesByPriceDescending(List<Technology> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getPrice() < list.get(j + 1).getPrice()) {
                    Technology temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }





    // TODO Persistence methods
    @Override
    public String fileName() {
        // 获取并返回文件的名称
        return String.valueOf(file);
    }

    @Override
    public void save() throws Exception {
        // 初始化XStream对象，用于XML序列化
        var xstream = new XStream(new DomDriver());
        // 创建对象输出流，准备写入文件
        var out = xstream.createObjectOutputStream(new FileWriter(file));
        // 将technologyList对象写入到XML文件
        out.writeObject(technologyList);
        // 关闭输出流
        out.close();
    }

    @Override
    public void load() throws Exception {
        // 定义XStream，并设置允许序列化的类类型
        var xstream = new XStream(new DomDriver());
        Class<?>[] allowedClasses = {Technology.class, SmartBand.class, SmartWatch.class, Tablet.class};
        // 设置XStream的默认安全配置并允许特定类的序列化
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(allowedClasses);

        // 检查文件是否存在，存在则进行反序列化
        if (file.exists()) {
            // 创建对象输入流，从文件读取XML数据
            var in = xstream.createObjectInputStream(new FileReader(file));
            // 读取对象，并将其转换为ArrayList<Technology>
            technologyList = (ArrayList<Technology>) in.readObject();
            // 关闭输入流
            in.close();
        } else {
            // 如果文件不存在，打印错误信息
            System.out.println("File not found: " + file.getName());
        }
    }

}
