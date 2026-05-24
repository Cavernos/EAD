package com.isep.ead.controllers;

import com.isep.ead.utils.SceneManager;
import javafx.stage.Stage;

public abstract class Controller {
    SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
