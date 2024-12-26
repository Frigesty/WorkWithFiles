package ru.frigesty.modal;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Phone {
    private String brand;
    private String model;
    private final List<String> navigationSystem = new ArrayList<>();
    private String operatingSystem;
    private Integer memoryGB;
    private CPU cpu;
    private Camera camera;
}