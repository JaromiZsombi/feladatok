package com.example.fovarosgui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelloController {


    public ListView fovarosLV;
    public TextField fovarosTXTF;
    public TextField fovarosLakossagTXTF;


    public class Fovaros {
        String orszag;
        String rovidites;
        int lakossag;
        String fovaros;
        int fovarosLakossag;

        public Fovaros(String line){
            String[] lineArr = line.split(";");
            orszag = lineArr[0];
            rovidites = lineArr[1];
            lakossag = Integer.parseInt(lineArr[2]);
            fovaros = lineArr[3];
            fovarosLakossag = Integer.parseInt(lineArr[4]);
        }

        @Override
        public String toString() {
            return String.format("%s (%,d fő): %s", orszag, lakossag, rovidites);
        }
    }

    private void betolt(File fajlnev){
        Scanner beolvasas = null;
        try {
            beolvasas = new Scanner(fajlnev, "utf-8");
            beolvasas.nextLine();
            while(beolvasas.hasNextLine()) fovarosok.add(new Fovaros(beolvasas.nextLine()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(beolvasas != null) beolvasas.close();
        }
    }
    private ObservableList<Fovaros> fovarosok = FXCollections.observableArrayList();

    private FileChooser fc = new FileChooser();

    public void initialize() {

        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV fájlok", "*.csv"));
        fovarosLV.setItems(fovarosok);

    }

    public void onOpenClick(ActionEvent actionEvent) {
        File fajl = fc.showOpenDialog(fovarosLV.getScene().getWindow());
        if(fajl != null) betolt(fajl);
    }

    public void onCloseClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onAboutClick(ActionEvent actionEvent) {
        Alert nevjegy = new Alert(Alert.AlertType.INFORMATION);
        nevjegy.setTitle("Névjegy");
        nevjegy.setHeaderText("");
        nevjegy.setContentText("Főváros v1.0.0\n(C)Kandó");
        nevjegy.showAndWait();
    }

    public void onSelectTile(MouseEvent mouseEvent) {
        int i = fovarosLV.getSelectionModel().getSelectedIndex();
        if(i != -1){
            fovarosTXTF.setText(fovarosok.get(i).fovaros);
            fovarosLakossagTXTF.setText(String.format("%,d fő", fovarosok.get(i).fovarosLakossag));
        }

    }
}
