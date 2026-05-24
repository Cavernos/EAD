package com.isep.ead.utils;

import com.isep.ead.EADApplication;
import com.isep.ead.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {


    private Stage mainStage;


    public SceneManager(Stage stage){
        this.setMainStage(stage);
    };

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    /**
     * @param view: The name of the fxml view
     * @return Controller|null
     */
    public Controller switchTo(String view) {
        URL viewPath = EADApplication.class.getResource(view + ".fxml");
        if (viewPath == null)
            return null;
        FXMLLoader fxmlLoader = new FXMLLoader(viewPath);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            this.mainStage.setScene(scene);
            this.mainStage.sizeToScene();
            Controller controller = fxmlLoader.getController();
            if (controller != null)
                controller.setSceneManager(this);
            return controller;
        } catch (IOException e) {
            System.out.println("Failed to load Scene : " + view);
            throw new RuntimeException(e);
        }
    }
}
