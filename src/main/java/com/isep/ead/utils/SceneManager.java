package com.isep.ead.utils;

import com.isep.ead.EADApplication;
import com.isep.ead.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SceneManager {

    private Stage mainStage;
    private Scene actualScene;
    private FXMLLoader lastLoader;

    public SceneManager(Stage stage){
        this.setMainStage(stage);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public Scene loadScene(String view) {
        try {
            this.lastLoader = this.loadPath(view);
            return new Scene(Objects.requireNonNull(this.lastLoader).load());
        } catch (IOException e) {
            System.out.println("Failed to load Scene : " + view);
            throw new RuntimeException(e);
        }
    }

    public Controller switchTo(String view) {
        Scene scene = this.loadScene(view);
        this.mainStage.setScene(scene);
        this.actualScene = scene;
        this.mainStage.sizeToScene();
        return this.getSceneController();

    }

    public Controller getSceneController() {
        if (this.lastLoader != null) {
            Controller controller = this.lastLoader.getController();
            if (controller != null)
                controller.setSceneManager(this);
            return controller;
        }
        return null;
    }

    private FXMLLoader loadPath(String view) {
        URL viewPath = EADApplication.class.getResource(view + ".fxml");
        if (viewPath == null) {
            System.out.println("The view " + view + " does not exists !");
            return null;
        }
        return new FXMLLoader(viewPath);
    }
}
