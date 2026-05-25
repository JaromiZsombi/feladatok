package com.example.utazokgui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloController {

    public ListView varosokLV;
    public ListView utazokLV;

    public void onListClick() {
        ObservableList<String> utazokList2 = FXCollections.observableArrayList();
        for (Utazok obj: utazokList){
            if (varosokLV.getSelectionModel().getSelectedItem().equals(obj.varos)){
                utazokList2.add(obj.nev+" ("+obj.honap+"."+obj.nap+" "+obj.ora+":"+obj.perc+")");
            }
        }
        utazokLV.setItems(utazokList2);
    }

    public class Utazok {
        String nev;
        String varos;
        int honap;
        int nap;
        int ora;
        int perc;

        public Utazok(String line){
            String[] lineArr = line.split(";");
            nev = lineArr[0];
            varos = lineArr[1];
            honap = Integer.parseInt(lineArr[2].split("\\.")[0]);
            nap = Integer.parseInt(lineArr[2].split("\\.")[1]);
            ora = Integer.parseInt(lineArr[3].split(":")[0]);
            perc = Integer.parseInt(lineArr[3].split(":")[1]);
        }

        @Override
        public String toString() {
            return String.format("%s %s %d.%d %d:%d", nev, varos, honap, nap, ora, perc);
        }
    }

    private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) utazokList.add(new Utazok(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }
    }
    private ObservableList<Utazok> utazokList = FXCollections.observableArrayList();

    private FileChooser fc = new FileChooser();

    private ObservableList<String> varosok = FXCollections.observableArrayList();

    public void initialize() {

        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));


    }

    public void onOpenClick(ActionEvent actionEvent) {
        File fajl = fc.showOpenDialog(varosokLV.getScene().getWindow());
        if(fajl != null) betolt(fajl);
        for (Utazok obj: utazokList){
            if (!varosok.contains(obj.varos)){
                varosok.add(obj.varos);
            }
        }
        varosokLV.setItems(varosok);
        varosokLV.getSelectionModel().select(0);
        onListClick();

    }

    public void onCloseClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onAboutClick(ActionEvent actionEvent) {
        Alert nevjegy = new Alert(Alert.AlertType.INFORMATION);
        nevjegy.setTitle("Névjegy");
        nevjegy.setHeaderText("");
        nevjegy.setContentText("Utazok v1.0.0\n(C)Kandó");
        nevjegy.showAndWait();
    }
}
