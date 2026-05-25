package com.example.madarakgui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HelloController {

    public ListView madarakLV;
    public TextField kisebbSuly;
    public TextField nagyobbSuly;
    public Button filterBtn;
    public Button allBtn;


    public class Madarak {
        String magyarNev;
        String latinNev;
        int atlagSuly;
        int atlagMagassag;
        int atlagReptav;

        public Madarak(String line) {
            String[] lineArr = line.split(";");
            magyarNev = lineArr[0];
            latinNev = lineArr[1];
            atlagSuly = Integer.parseInt(lineArr[2]);
            atlagMagassag = Integer.parseInt(lineArr[3]);
            atlagReptav = Integer.parseInt(lineArr[4]);
        }

        @Override
        public String toString() {
            return String.format("%s (%s): %d g", magyarNev, latinNev, atlagSuly);
        }
    }

    private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) madarak.add(new Madarak(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }
    }
    private ObservableList<Madarak> madarak = FXCollections.observableArrayList();

    private FileChooser fc = new FileChooser();

    public void initialize() {

        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        madarakLV.setItems(madarak);

    }






    public void onOpenClick() {
        File fajl = fc.showOpenDialog(madarakLV.getScene().getWindow());
        if(fajl != null) betolt(fajl);
        madarak.sort(Comparator.comparingInt(m->m.atlagSuly));
        filterBtn.setDisable(false);
        allBtn.setDisable(false);
    }

    public void onCloseClick() {
        Platform.exit();
    }

    public void onAboutClick() {
        Alert nevjegy = new Alert(Alert.AlertType.INFORMATION);
        nevjegy.setTitle("Névjegy");
        nevjegy.setHeaderText("");
        nevjegy.setContentText("Madarak v1.0.0\n(C)Kandó");
        nevjegy.showAndWait();
    }

    public void onFilterClick(){
        int minSuly = Integer.parseInt(kisebbSuly.getText());
        int maxSuly = Integer.parseInt(nagyobbSuly.getText());
        madarakLV.setItems(madarak.stream()
                .filter(obj->obj.atlagSuly>=minSuly && obj.atlagSuly<=maxSuly)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

    }
    public void onAllClick(){
        kisebbSuly.setText("");
        nagyobbSuly.setText("");
        madarakLV.setItems(madarak);
    }
}
