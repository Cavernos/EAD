package com.isep.ead;


import com.isep.ead.controllers.MainController;
import com.isep.ead.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class EADApplication extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = new SceneManager(stage);
        MainController controller = (MainController) sceneManager.switchTo(sceneManager.loadPage("menu-view"));
        controller.dashboard();
        stage.setTitle("EAD");
        stage.show();

    }
}
