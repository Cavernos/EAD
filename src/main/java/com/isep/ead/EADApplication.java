package com.isep.ead;


import com.isep.ead.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class EADApplication extends Application {
    @Override
    public void start(Stage stage) {

        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.switchTo("organisation-view");
        stage.setTitle("EAD");
        stage.show();


    }
}
