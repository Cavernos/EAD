package com.isep.ead;


import com.isep.ead.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EADApplication extends Application {
    @Override
    public void start(Stage stage) {

        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.switchTo("homeview");
        stage.setTitle("EAD");
        stage.show();


    }
}
