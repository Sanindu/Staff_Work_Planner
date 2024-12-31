package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkDetailControl {
    private List<WorkDetail> workDetails;
    private final String filePath;

    public WorkDetailControl(String filePath) {
        this.filePath = filePath;
        this.workDetails = loadFromFile();
    }

    public void addWorkDetail(WorkDetail workDetail) {
        workDetails.add(workDetail);
    }

    public void removeWorkDetail(int index) {
        if (index >= 0 && index < workDetails.size()) {
            workDetails.remove(index);
        } else {
            System.out.println("Invalid index.");
        }
    }

    public void displayWorkDetails() {
        for (WorkDetail workDetail : workDetails) {
            System.out.println(workDetail);
        }
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(workDetails);
            System.out.println("Work details saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<WorkDetail> loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<WorkDetail>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
