package models;

import utils.Utilities;

public abstract class ComputingDevice extends Technology {
    private String processor;
    private int storage;

    public ComputingDevice(String modelName, double price, Manufacturer manufacturer, String id, String processor, int storage) {
        super(modelName, price, manufacturer, id);
        this.processor = Utilities.truncateString(processor, 20);
        setStorage(storage);
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        if (Utilities.validStringlength(processor, 20)) {
            this.processor = Utilities.truncateString(processor, 20);
        } else if (this.processor == null || this.processor.isEmpty()) {
            this.processor = "unknown"; // 默认值如果之前未设置处理器
        }
    }


    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        if (Utilities.isValidStorage(storage)) {
            this.storage = storage;
        } else if (this.storage == 0) {
            this.storage = 8; // 默认值如果之前未设置存储
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n" + ", Processor: " + processor +
                ", Storage: " + storage + "GB";
    }
}
