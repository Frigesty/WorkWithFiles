package ru.frigesty.modal;

import java.util.ArrayList;
import java.util.List;

public class Phone {
    private String brand;
    private String model;
    private final List<String> navigationSystem = new ArrayList<>();
    private String operatingSystem;
    private Integer memoryGB;
    private CPU cpu;
    private Camera camera;

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public List<String> getNavigationSystem() {
        return navigationSystem;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public Integer getMemoryGB() {
        return memoryGB;
    }

    public CPU getCPU() {
        return cpu;
    }

    public Camera getCamera() {
        return camera;
    }
}