package com.dan.spatialdirectorypreviewer;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/* Spatial Directory Previewer Copyright (C) 2022  Daniel Nelson

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/



public class MainApp extends Application {

    private final String APPLICATION_VERSION = "Version 0.5";
    private final String APPLICATION_AUTHOR = "\u00A9 2022 Daniel Nelson";
    private final String APPLICATION_WINDOW_TITLE = "Spatial Directory Previewer";

    private ArrayList<String> SelectedFileTypes = new ArrayList<>();

    private ArrayList<File> FileList = new ArrayList<>();
    private ObservableList<String> contentList = FXCollections.observableArrayList();
    private File currentDirectory;

    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");
    Menu editMenu = new Menu("Edit");
    Menu filterMenu = new Menu("Filter");
    final Label CellSize = new Label("Cell Size");
    final Label GCS = new Label("GC System");
    final Label PCS = new Label("PC System");
    final Label Bands = new Label("Number of Bands");
    final Label coords = new Label("Top Left Coordinates");

    Button RefreshButton = new Button("Search");
    Button PreviewFile = new Button("Preview");
    Text ConsoleArea = new Text();
    Text selectedFileName = new Text();
    Text selectedFilePath = new Text();

    private void buildMenus(Stage mainstage){
        //build the menus for the menu bar
        MenuItem aboutappMenuItem = new MenuItem("About App");
        fileMenu.getItems().addAll(aboutappMenuItem);
        aboutappMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert popupalert = new Alert(Alert.AlertType.INFORMATION);
                popupalert.setTitle("About App Dialogue");
                popupalert.setHeaderText(null);
                popupalert.setContentText(APPLICATION_VERSION + " " + APPLICATION_AUTHOR);
                popupalert.showAndWait();
            }
        });

        MenuItem guideMenuItem = new MenuItem("Guide");
        fileMenu.getItems().addAll(guideMenuItem);
        guideMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert guidealert = new Alert(Alert.AlertType.INFORMATION);
                guidealert.setTitle("Usage Guide");
                guidealert.setHeaderText(null);
                guidealert.setContentText("Fill this in.");
                guidealert.showAndWait();
            }
        });

        MenuItem folderMenuItem = new MenuItem("Folder");
        editMenu.getItems().addAll(folderMenuItem);
        folderMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DirectoryChooser getTopDirectory = new DirectoryChooser();
                getTopDirectory.setInitialDirectory(new File(System.getProperty("user.home")));
                getTopDirectory.setTitle("Select Root Folder");
                currentDirectory = getTopDirectory.showDialog(mainstage);
                //Add all videos to ArrayList
                Refresh();
                clearConsole();
            }
        });

        MenuItem filterMenuItem = new MenuItem("File Types");
        filterMenu.getItems().addAll(filterMenuItem);
        //set core spatial file types
        SelectedFileTypes.add(".shp");
        SelectedFileTypes.add(".tif");
        SelectedFileTypes.add(".tiff");
        filterMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog askTypes = new TextInputDialog();
                askTypes.setTitle("Enter Custom Extensions");
                askTypes.setHeaderText("Filter by a custom file extension (Example: .jpeg");
                askTypes.setContentText("Extension: ");

                Optional<String> extResult = askTypes.showAndWait();
                if (extResult.isPresent()){
                    SelectedFileTypes.add(askTypes.getEditor().getText().strip());
                }

            }
        });
    }
    private File selectFile(String yourFileName) {
        File yourfile = null;

        for (File f : FileList){
            if (f.getName().equals(yourFileName)){
                yourfile = f;
                break;
            }
        }

        if (yourfile == null){
            ConsoleArea.setText("Issue with file matching.");
        }

        return yourfile;
    }

    private void addFiles(File selectedDirectory) {
        FileList.clear();
        getFiles(selectedDirectory, FileList);
    }

    private void getFiles(File directory, ArrayList<File> gsfiles){
        File[] listedFiles = directory.listFiles();
        if (SelectedFileTypes == null){
            ConsoleArea.setText("Select geospatial file types!");
        }
        if (listedFiles != null){
            for (File file : listedFiles){
                //TODO: this is broken String.valueOf(SelectedFileTypes)
                if (file.isFile() && (file.getName().endsWith(".tif") || file.getName().endsWith(".shp"))){
                    gsfiles.add(file);
                } else if (file.isDirectory()) {
                    File thisdirectory = new File(file.getAbsolutePath());
                    getFiles(thisdirectory, gsfiles);
                } else {
                    ConsoleArea.setText("Directory Empty");
                }
            }
        } else {
            ConsoleArea.setText("Select directory with geospatial files!");
        }
    }

    private void showFileInfo(String selectedFile) {
        if (selectFile(selectedFile) != null){
            selectedFileName.setText(selectedFile);
            selectedFilePath.setText(selectFile(selectedFile).toString());
            createPreview(selectFile(selectedFile));
        }

    }

    private void showPreview(){

    }

    private void createPreview(File filetopreview){
        //create screenshot

    }

    private void clearSelected(){
        selectedFileName.setText("");
        selectedFilePath.setText("");
        //set other properties to null?
    }

    private void clearConsole(){ConsoleArea.setText("");
    }

    private void Refresh(){
        try{
            addFiles(currentDirectory);
            contentList.clear();
            for (File f : FileList){
                contentList.add(f.getName());
            }
        }catch (Exception e){
            ConsoleArea.setText("Select directory with files!");
        }

    }

    @Override
    public void start(Stage rootstage) throws IOException {

        ListView<String> geoList = new ListView<String>();
        BackgroundFill darkgray = new BackgroundFill(Color.web("#8d948d"), CornerRadii.EMPTY, Insets.EMPTY);
        Background menuColor = new Background(darkgray);
        Separator consoleLine = new Separator(Orientation.HORIZONTAL);

        SplitPane mainPane = new SplitPane();

        //add HBox for console output
        HBox bottomBox = new HBox();
        VBox vBox = new VBox(menuBar);
        VBox vBoxProperties = new VBox();
        VBox vBoxList = new VBox();
        Scene scene = new Scene(vBox, 800, 600);
        rootstage.setResizable(false);
        rootstage.setScene(scene);
        rootstage.show();
        rootstage.setTitle(APPLICATION_WINDOW_TITLE);

        //TODO: add file properties to locations

        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(editMenu);
        menuBar.getMenus().add(filterMenu);
        menuBar.setBackground(menuColor);
        buildMenus(rootstage);

        //Set placement for items to draw
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        RefreshButton.setAlignment(Pos.TOP_LEFT);
        PreviewFile.setAlignment(Pos.TOP_LEFT);

        bottomBox.getChildren().addAll(ConsoleArea);
        vBoxList.getChildren().addAll(RefreshButton, geoList);
        vBoxProperties.getChildren().addAll(PreviewFile, selectedFileName, GCS, PCS, CellSize, Bands, coords);

        mainPane.getItems().addAll(vBoxList,vBoxProperties);
        vBox.getChildren().addAll(mainPane, consoleLine, bottomBox, selectedFilePath);

        ConsoleArea.setText("Application Started!");

        geoList.setItems(contentList);
        geoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        geoList.setOrientation(Orientation.VERTICAL);
        geoList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        showFileInfo(newValue);
                    }
                }
        );

        RefreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(FileList.size() > 0){
                    Refresh();
                    //clear selected file
                    clearSelected();
                    //just in-case
                    clearConsole();
                }else{
                    ConsoleArea.setText("Select directory with files!");
                }
            }
        });

        PreviewFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    //show preview
                    showPreview();
                }catch (Exception e){
                    ConsoleArea.setText("Error displaying image.");
                }

            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}