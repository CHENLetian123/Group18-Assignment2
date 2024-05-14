package controllers;

import models.*;

import utils.ISerializer;
import utils.OperatingSystemUtility;

import utils.Utilities;

import java.io.*;
import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


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
        if (isValidId(technology.getId())) { // 在添加前先检查ID是否有效
            technologyList.add(technology);
            return true;
        }
        return false; // 如果ID无效，则不添加设备
    }



    // Delete methods
    public boolean removeTechnology(Technology technology) {
        return technologyList.remove(technology);
    }

    // 更新智能手表
    public boolean updateSmartWatch(String id, SmartWatch updatedDetails) {
        for (int i = 0; i < technologyList.size(); i++) {
            Technology tech = technologyList.get(i);
            if (tech instanceof SmartWatch && tech.getId().equalsIgnoreCase(id)) {
                technologyList.set(i, updatedDetails);
                return true;
            }
        }
        return false;
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
        StringBuilder list = new StringBuilder();
        for (Technology tech : technologyList) {
            if (tech instanceof Tablet) {
                list.append(tech.toString()).append("\n");
            }
        }
        return list.toString();
    }
    public String listAllSmartWatches() {
        StringBuilder list = new StringBuilder();
        for (Technology tech : technologyList) {
            if (tech instanceof SmartWatch) {
                list.append(tech.toString()).append("\n");
            }
        }
        return list.toString();
    }

    public String listAllSmartBands() {
        StringBuilder list = new StringBuilder();
        for (Technology tech : technologyList) {
            if (tech instanceof SmartBand) {
                list.append(tech.toString()).append("\n");
            }
        }
        return list.toString();
    }
    // 列出所有技术设备
    public String listAllTechnologyDevices() {
        if (technologyList.isEmpty()) {
            return "No Technology Devices";
        }
        StringBuilder sb = new StringBuilder();
        for (Technology tech : technologyList) {
            sb.append(tech.toString()).append("\n");
        }
        return sb.toString();
    }

    // 列出所有价格高于某值的技术设备
    public String listAllTechnologyAbovePrice(double price) {
        StringBuilder sb = new StringBuilder();
        for (Technology tech : technologyList) {
            if (tech.getPrice() >= price) {
                sb.append(tech.toString()).append("\n");
            }
        }
        return sb.length() == 0 ? "No technology more expensive than €" + price : sb.toString();
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

    // 设备数量统计
    public int numberOfTechnologyDevices() {
        return technologyList.size();
    }

   //TODO - delete methods
    // 通过ID删除技术设备
   public boolean deleteTechnologyById(String id) {
       for (int i = 0; i < technologyList.size(); i++) {
           if (technologyList.get(i).getId().equalsIgnoreCase(id)) {
               technologyList.remove(i);
               return true;
           }
       }
       return false;
   }


    //TODO - sort methods

    // 按价格降序排序
    public void sortByPriceDescending() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < technologyList.size() - 1; i++) {
                if (technologyList.get(i).getPrice() < technologyList.get(i + 1).getPrice()) {
                    Technology temp = technologyList.get(i);
                    technologyList.set(i, technologyList.get(i + 1));
                    technologyList.set(i + 1, temp);
                    swapped = true;
                }
            }
        } while (swapped);
    }


    private void swapTechnology(int i, int j) {
        Technology temp = technologyList.get(i);
        technologyList.set(i, technologyList.get(j));
        technologyList.set(j, temp);
    }

    //TODO Top 5 methods

    // 获取Top 5最昂贵的技术设备
    public List<Technology> topFiveMostExpensiveTechnology() {
        List<Technology> sortedList = new ArrayList<>(technologyList);
        sortTechnologiesByPriceDescending(sortedList);
        return sortedList.subList(0, Math.min(5, sortedList.size()));
    }

    // 获取Top 5最昂贵的智能手表
    public List<Technology> topFiveMostExpensiveSmartWatch() {
        List<Technology> filteredAndSorted = new ArrayList<>();
        for (Technology tech : technologyList) {
            if (tech instanceof SmartWatch) {
                filteredAndSorted.add(tech);
            }
        }
        sortTechnologiesByPriceDescending(filteredAndSorted);
        return filteredAndSorted.subList(0, Math.min(5, filteredAndSorted.size()));
    }

    // 获取Top 5最昂贵的平板
    public List<Technology> topFiveMostExpensiveTablet() {
        List<Technology> filteredAndSorted = new ArrayList<>();
        for (Technology tech : technologyList) {
            if (tech instanceof Tablet) {
                filteredAndSorted.add(tech);
            }
        }
        sortTechnologiesByPriceDescending(filteredAndSorted);
        return filteredAndSorted.subList(0, Math.min(5, filteredAndSorted.size()));
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
